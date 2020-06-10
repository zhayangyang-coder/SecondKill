<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2020/6/8
  Time: 20:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>尚筹网-秒杀页面</title>
    <script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
</head>
<body>
    <form action="${pageContext.request.contextPath}/secondKill" method="post">
        <input type="hidden" name="id" value="10001">
        <a href="#">一元秒杀iPhone 11</a>
    </form>
</body>
<script type="text/javascript">
    $("a").click(function () {
        $.ajax({
            url: $("form").prop("action"),
            type: "post",
            data: $("form").serialize(),
            success: function (res) {
                if (res == "ok") {
                    alert("秒杀成功");
                } else {
                    alert(res);
                }
            }
        });
        return false;
    });
</script>
</html>
