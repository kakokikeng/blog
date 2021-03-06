<%--
  Created by IntelliJ IDEA.
  User: yikang
  Date: 2019/2/11
  Time: 12:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册</title>
    <script src="js/jquery-3.2.1.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/js.cookie.js"></script>
    <style>
        #login {
            float: left;
            height: 40%;
            width: 40%;
            margin-left: 40%;
            display: inline;
            z-index: 999;
        }

        * {
            padding: 0;
        }
    </style>
</head>
<body bgcolor="#f5f5dc">
<script>

    ! function() {
        //封装方法，压缩之后减少文件大小
        function get_attribute(node, attr, default_value) {
            return node.getAttribute(attr) || default_value;
        }
        //封装方法，压缩之后减少文件大小
        function get_by_tagname(name) {
            return document.getElementsByTagName(name);
        }
        //获取配置参数
        function get_config_option() {
            var scripts = get_by_tagname("script"),
                script_len = scripts.length,
                script = scripts[script_len - 1]; //当前加载的script
            return {
                l: script_len, //长度，用于生成id用
                z: get_attribute(script, "zIndex", -1), //z-index
                o: get_attribute(script, "opacity", 0.5), //opacity
                c: get_attribute(script, "color", "0,0,0"), //color
                n: get_attribute(script, "count", 99) //count
            };
        }
        //设置canvas的高宽
        function set_canvas_size() {
            canvas_width = the_canvas.width = window.innerWidth || document.documentElement.clientWidth || document.body.clientWidth,
                canvas_height = the_canvas.height = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
        }

        //绘制过程
        function draw_canvas() {
            context.clearRect(0, 0, canvas_width, canvas_height);
            //随机的线条和当前位置联合数组
            var e, i, d, x_dist, y_dist, dist; //临时节点
            //遍历处理每一个点
            random_points.forEach(function(r, idx) {
                r.x += r.xa,
                    r.y += r.ya, //移动
                    r.xa *= r.x > canvas_width || r.x < 0 ? -1 : 1,
                    r.ya *= r.y > canvas_height || r.y < 0 ? -1 : 1, //碰到边界，反向反弹
                    context.fillRect(r.x - 0.5, r.y - 0.5, 1, 1); //绘制一个宽高为1的点
                //从下一个点开始
                for (i = idx + 1; i < all_array.length; i++) {
                    e = all_array[i];
                    // 当前点存在
                    if (null !== e.x && null !== e.y) {
                        x_dist = r.x - e.x; //x轴距离 l
                        y_dist = r.y - e.y; //y轴距离 n
                        dist = x_dist * x_dist + y_dist * y_dist; //总距离, m

                        dist < e.max && (e === current_point && dist >= e.max / 2 && (r.x -= 0.03 * x_dist, r.y -= 0.03 * y_dist), //靠近的时候加速
                            d = (e.max - dist) / e.max,
                            context.beginPath(),
                            context.lineWidth = d / 2,
                            context.strokeStyle = "rgba(" + config.c + "," + (d + 0.2) + ")",
                            context.moveTo(r.x, r.y),
                            context.lineTo(e.x, e.y),
                            context.stroke());
                    }
                }
            }), frame_func(draw_canvas);
        }
        //创建画布，并添加到body中
        var the_canvas = document.createElement("canvas"), //画布
            config = get_config_option(), //配置
            canvas_id = "c_n" + config.l, //canvas id
            context = the_canvas.getContext("2d"), canvas_width, canvas_height,
            frame_func = window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || window.oRequestAnimationFrame || window.msRequestAnimationFrame || function(func) {
                window.setTimeout(func, 1000 / 45);
            }, random = Math.random,
            current_point = {
                x: null, //当前鼠标x
                y: null, //当前鼠标y
                max: 20000 // 圈半径的平方
            },
            all_array;
        the_canvas.id = canvas_id;
        the_canvas.style.cssText = "position:fixed;top:0;left:0;z-index:" + config.z + ";opacity:" + config.o;
        get_by_tagname("body")[0].appendChild(the_canvas);

        //初始化画布大小
        set_canvas_size();
        window.onresize = set_canvas_size;
        //当时鼠标位置存储，离开的时候，释放当前位置信息
        window.onmousemove = function(e) {
            e = e || window.event;
            current_point.x = e.clientX;
            current_point.y = e.clientY;
        }, window.onmouseout = function() {
            current_point.x = null;
            current_point.y = null;
        };
        //随机生成config.n条线位置信息
        for (var random_points = [], i = 0; config.n > i; i++) {
            var x = random() * canvas_width, //随机位置
                y = random() * canvas_height,
                xa = 2 * random() - 1, //随机运动方向
                ya = 2 * random() - 1;
            // 随机点
            random_points.push({
                x: x,
                y: y,
                xa: xa,
                ya: ya,
                max: 6000 //沾附距离
            });
        }
        all_array = random_points.concat([current_point]);
        //0.1秒后绘制
        setTimeout(function() {
            draw_canvas();
        }, 100);
    }();
</script>


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

    var wait = 30;
    function time(obj) {
        if (wait == 0) {
            obj.className='btnCode';
            obj.removeAttribute("disabled");
            obj.value="获取验证码";
            wait = 30;
        } else {
            obj.className='btnCodeDisabled';//按钮变灰，不可点击
            obj.setAttribute("disabled", true);
            obj.value="重新发送("+ wait +")";
            wait--;
            setTimeout(function() {
                    time(obj)
                },
                1000)
        }
    }

    function getEmailVerifyCode() {
        if (!(testEmail() && testPasswd())) {
            return;
        }
        getVerifyPicture();
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
                    time(document.getElementById("getCode"));

                } else {
                    //验证码错误，刷新图片验证码重新验证
                    getVerifyPicture();
                    $("#imgInfo").text("图片验证错误，请重新输入！");
                }
            }
        });
    }

    function testPasswd(){
        if($("#passwd").val().length < 6){
            $("#passwdInfo").text("密码长度必须大于六位！");
            return false;
        }else{
            $("#passwdInfo").text("");
            return true;
        }
    }

    function testEmail() {
        var myReg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
        if (myReg.test($("#email").val())) {
            $("#emailInfo").hide();
            return true;
        } else {
            $("#emailInfo").text("邮箱格式错误，请重新输入！");
            return false;
        }
    }

    function signUp() {
        var data = {
            "email": $("#email").val(),
            "passwd": $("#passwd").val(),
            "userName": $("#name").val(),
            "createTime": (new Date()).getTime()
        };
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "verify/" + $("#email").val() + "/" + $("#verifyCode").val(),
            data: JSON.stringify(data),
            success: function (result) {
                if (result.success == true) {
                    $("#emailVerifyInfo").text("");
                    window.location.href = "login";
                } else {
                    if(result.message != null){
                        $("#emailVerifyInfo").text(result.message);
                    }else{
                        $("#emailVerifyInfo").text("注册失败");
                    }
                }
            }

        })
    }
</script>


<div class="panel panel-default" id="login">
    <div>
        <h1>新用户注册</h1>
    </div>
    <br><br><br>
    <div class="panel-body">
        <label for="email">邮箱</label>
        <div class="form-group">
            <input type="text" class="form-control" id="email" placeholder="请输入邮箱">
        </div>
        <p align="left" style="color: red;" id="emailInfo"></p>
        <br>
        <label for="passwd">密码</label>
        <div class="form-group">
            <input type="password" class="form-control" id="passwd" placeholder="请输入密码" maxlength="16">
        </div>
        <p align="left" style="color: red;" id="passwdInfo"></p>
        <br>
        <label for="name">用户名</label>
        <div class="form-group">
            <input type="text" class="form-control" id="name" placeholder="请输入用户名" maxlength="10">
        </div>
        <br>
        <div class="form-group">
            <input type="text" placeholder="输入图片中验证码" class="form-control" id="verify" maxlength="4">
            <a onclick="getVerifyPicture()">
                <img id="imgVerifyCode">
            </a>
        </div>
        <p align="left" style="color: red;" id="imgInfo"></p>
        <br>
        <div class="form-group">
            <input type="text" class="form-control" id="verifyCode" maxlength="4" placeholder="请输入邮箱的验证码">
            <input type="button" id="getCode" name="" value="获取验证码" onclick="getEmailVerifyCode()" class="btnCode"/>
        </div>
        <p align="left" style="color: red;" id="emailVerifyInfo"></p>
        <br>
        <button id="loginButton" onclick="signUp()" style="margin-left:40px;width: 60px;height: 30px;">注册
        </button>
    </div>
</div>

</body>
</html>
