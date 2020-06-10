package com.atguigu.sk.service.impl;

import com.atguigu.sk.service.SecondKillService;
import com.atguigu.sk.util.JedisPoolUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

@Service
public class SecondKillServiceImpl implements SecondKillService {
    // lua脚本
    static String secKillScript = "local userid=KEYS[1];\r\n"
            + "local prodid=KEYS[2];\r\n"
            + "local qtkey='product_id:'..prodid..\":st\";\r\n"
            + "local usersKey='product_id:'..prodid..\":usr\";\r\n"
            + "local userExists=redis.call(\"sismember\",usersKey,userid);\r\n"
            + "if tonumber(userExists)==1 then \r\n"
            + "   return 2;\r\n"
            + "end\r\n"
            + "local num= redis.call(\"get\" ,qtkey);\r\n"
            + "if tonumber(num)<=0 then \r\n"
            + "   return 0;\r\n"
            + "else \r\n"
            + "   redis.call(\"decr\",qtkey);\r\n"
            + "   redis.call(\"sadd\",usersKey,userid);\r\n"
            + "end\r\n"
            + "return 1";


    @Override
    public String secondKill(String id) {
        int userId = (int)(Math.random() * 10000);
        // 从redis连接池中获取连接
        Jedis jedis = JedisPoolUtil.getJedisPoolInstance().getResource();
        // 加载lua脚本
        String scriptLoad = jedis.scriptLoad(secKillScript);
        // 执行lua脚本
        Object evalsha = jedis.evalsha(scriptLoad, 2, userId + "", id);
        // 将返回结果转换为int
        int resNum = (int)((long)evalsha);
        String result = null;
        if (resNum == 1) {
            System.out.println("用户'" + userId + "'成功秒杀！");
            result = "ok";
        } else if (resNum == 2) {
            System.out.println("抱歉，用户" + userId + "已参与秒杀！");
            result = "抱歉，用户'" + userId + "'已参与秒杀！";
        } else {
            System.out.println("抱歉，库存不足！");
            result = "抱歉，库存不足！";
        }
        jedis.close();
        return result;
//        String productKey = "product_id:" + id + ":st";
//        String userKey = "user" + id + ":list";
        //随机生成用户的id
//        int userId = (int)(Math.random() * 10000);
        //获取连接
//        Jedis jedis = new Jedis("192.168.150.128", 6379);
        // 判断当前用户是否已在秒杀成功的用户列表中
//        if (jedis.sismember(userKey, userId+"")) {
//            System.out.println("抱歉，用户" + userId + "已参与秒杀！");
//            return "抱歉，用户" + userId + "已参与秒杀！";
//        }
        // 监控产品库存
//        jedis.watch(productKey);
        // 获取产品库存
//        int stock = Integer.parseInt(jedis.get(productKey));
//        String result = null;
//        if (stock > 0) {
        // 获取redis事务队列
//            Transaction multi = jedis.multi();
        // 将decr命令操作加入到事务队列中
//            Response<Long> decr = multi.decr(productKey);
        // 执行事务队列中的命令
//            multi.exec();
//            if (!StringUtils.isEmpty(decr)) {
//                System.out.println("用户" + userId + "秒杀成功！");
//                result = "ok";
////                return "ok";
//            } else {
////                System.out.println("秒杀失败！");
//                System.out.println("秒杀失败！");
//                result = "秒杀失败！";
////                return "秒杀失败！";
//            }
//        } else {
////            System.out.println("抱歉，秒杀已结束！");
//            System.out.println("抱歉，库存不足！");
//            result = "抱歉，库存不足！";
////            return "抱歉，秒杀已结束！";
//        }
        // 关闭连接
//        jedis.close();
//        return result;
    }
}
