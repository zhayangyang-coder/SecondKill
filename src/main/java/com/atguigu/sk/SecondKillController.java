package com.atguigu.sk;

import com.atguigu.sk.service.SecondKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecondKillController {
    @Autowired
    SecondKillService secondKillService;

    @RequestMapping(value = "secondKill", produces = {"text/html;charset=UTF-8"})
    public String secondKill(String id) {
        String result = secondKillService.secondKill(id);
        return result;
    }
}
