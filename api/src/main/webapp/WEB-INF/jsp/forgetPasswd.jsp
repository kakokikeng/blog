<%--
  Created by IntelliJ IDEA.
  User: yikang
  Date: 2019/2/11
  Time: 12:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>忘记密码</title>
</head>
<body onload="getVerifyPicture()">

<script>
    function getVerifyPicture(){
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "authority",
            data: JSON.stringify(data),
            dataType: "json",
            success: function(data) {
                if(data.success == false) {
                    $("#info").text("提示:账号或密码错误！");
                }else if(data.success == true){
                    rememberToken(data.data);
                    if(remember){
                        rememberLogin(id,passwd,remember);
                    }else {
                        Cookies.remove('loginStatus');
                    }
                    $("#info").text("提示:登陆成功，跳转中...");
                    window.location.href="/reader_main.html";

                }
            }
        })
    }
    function getEmailVerifyCode() {
        var email = document.getElementById("email").value;
    }
    !function () {
        window.location.href="jsp/index.jsp";
    }
</script>

<div class="panel-body" align="center">
    <div class="form-group">
        <label for="email">你的邮箱</label>
        <input type="text" class="form-control" id="email" placeholder="请输入你的邮箱">
    </div>
    <div class="form-group">
        <input type="text" class="form-control" id="verify">
    </div>
    <button value="获取验证码" onclick="getEmailVerifyCode()"/>
    <div class="form-group">
        <label for="verifyCode">请输入验证码</label>
        <input type="password" class="form-control" id="verifyCode">
    </div>



</div>



</body>
</html>
