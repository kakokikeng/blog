<%--
  Created by IntelliJ IDEA.
  User: yikang
  Date: 2019/2/17
  Time: 17:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>changePasswd</title>
    <script src="js/jquery-3.2.1.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/js.cookie.js"></script>
</head>
<body>


<script>
    function changePasswd() {
        if($("#newPasswd").val().length < 6){
            $("#passwdInfo").text("密码长度必须大于六位！");
            return;
        }
        if ($("#newPasswd").val() != $("#ensurePasswd").val()) {
            $("#passwdInfo").text("两次密码不一致，请重新输入！");
            return;
        } else {
            $("#passwdInfo").text("");
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "user/" + Cookies.get("loginEmail") + "/" + $("#newPasswd").val(),
                success: function (result) {
                    if (result.success == true) {
                        window.location.href = "success";
                    } else {
                        window.location.href = "failure";
                    }

                }
            })
        }


    }
</script>


<h1 align="center">更改密码</h1>
<hr>
<hr>
<div class="panel-body" align="center">
    <div class="form-group">
        <label for="newPasswd">请输入新密码</label>
        <input type="password" class="form-control" id="newPasswd" maxlength="16">
    </div>
    <div class="form-group">
        <label for="ensurePasswd">请再次输入密码</label>
        <input type="password" class="form-control" id="ensurePasswd" maxlength="16">
    </div>

    <p style="text-align: right;color: red;position: absolute" id="passwdInfo"></p><br/>
    <button id="submit" onclick="changePasswd()" class="btn btn-primary  btn-block">提交
    </button>
</div>
</body>
</html>
