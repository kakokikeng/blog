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
    <script src="js/jquery-3.2.1.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/js.cookie.js"></script>
</head>
<body bgcolor="#f5f5dc">

<script>
    var tmp = "default";

    $(document).ready(getVerifyPicture());

    function getVerifyPicture() {
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "recaptcha/key",
            success: function (data) {
                tmp = data;
                $("#imgVerifyCode").attr("src", "/recaptcha/imgValidateCode/" + data);
            }
        });
    }

    function getEmailVerifyCode() {
        if(!testEmail()){
            return;
        }
        var data = {"key": tmp, "verifyCode": $("#verify").val()};
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "recaptcha/validation",
            data: JSON.stringify(data),
            success: function (result) {
                if (result.success == true) {
                    $("#imgInfo").hide();
                    //成功即验证码正确，发送请求邮箱验证码
                    $.ajax({
                        type: "GET",
                        contentType: "application/json",
                        url: "verify/" + $("#email").val()
                    })

                } else {
                    //验证码错误，刷新图片验证码重新验证
                    getVerifyPicture();
                    $("#imgInfo").text("图片验证错误，请重新输入！");
                }
            }
        });
    }

    function verifyEamil(){
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "verify/" + $("#email").val() + "/" + $("#verifyCode").val(),
            success: function (result) {
                if(result.success == true){
                    $("#emailVerifyInfo").text("");
                    Cookies.set('loginEmail',$("#email").val());
                    window.location.href="changePasswd";
                }else{
                    $("#emailVerifyInfo").text(result.message);
                }
            }
        })


    }

        function testEmail() {
            var myReg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
            if(myReg.test($("#email").val())){
                $("#emailInfo").hide();
                return true;
            }else{
                $("#emailInfo").text("邮箱格式错误，请重新输入！");
                return false;
            }
        }

</script>

<div class="panel-body" align="center">
    <h1>找回密码</h1>
    <br>
    <br>
    <br>
    <br>
    <div style="size: 3px;color: #66afe9;margin-left: 10px;">
        您的邮箱
    </div>
    <input type="text" id="email" placeholder="请输入您的邮箱">
    <p style="text-align: right;color: red;position: absolute" id="emailInfo"></p><br/>

    <br>
    <br>
    <br>
    <br>
    <div class="form-group">
        <input type="text" placeholder="输入图片中验证码" class="form-control" id="verify" maxlength="4">
        <a onclick="getVerifyPicture()">
            <img id="imgVerifyCode">
        </a>
    </div>
    <p style="text-align: right;color: red;position: absolute" id="imgInfo"></p><br/>
    <br>
    <br>

    <p style="text-align: right;color: red;position: absolute" id="emailVerifyInfo"></p><br/>
    <button onclick="getEmailVerifyCode()">获取验证码</button>
    <br>
    <br>
    <div class="form-group">
        <input type="text" class="form-control" id="verifyCode" maxlength="4">
    </div>
    <br>
    <button onclick="verifyEamil()">提交</button>



</div>



</body>
</html>
