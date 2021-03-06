<%--
  Created by IntelliJ IDEA.
  User: yikang
  Date: 2019/3/8
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>博客</title>
    <script src="js/jquery-3.2.1.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/js.cookie.js"></script>
</head>
<body bgcolor="#f5f5dc">

<script>
    !function () {
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
            random_points.forEach(function (r, idx) {
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
            frame_func = window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || window.oRequestAnimationFrame || window.msRequestAnimationFrame || function (func) {
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
        window.onmousemove = function (e) {
            e = e || window.event;
            current_point.x = e.clientX;
            current_point.y = e.clientY;
        }, window.onmouseout = function () {
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
        setTimeout(function () {
            draw_canvas();
        }, 100);
    }();
</script>

<div id="slogan" align="left" style="width: 50%;height: 25px;float: left;color: #888888;">
    Modesty helps one to go forward.
</div>
<a href="index" style="text-decoration: none;">返回主页</a>
<a id="userName" href="userPage"></a>
<div>
    <hr color="#D1D1D1">
</div>

<div id="box" style="width: 80%;margin-top: 50px;margin-left: 10%;border: #cccccc solid;">
    <div id="title"></div>
    <div id="readCount" align="center"></div>
    <div id="content" style="padding-left: 15px;padding-right: 15px;">
    </div>
</div>
<div id="menu-list" style="width:80%;height:150px;margin-left:10%;margin-top: 50px;border: #cccccc solid;">
    <div id="thumbs-up" align="center"
         style="height:100px;width:100px;margin-top: 30px;margin-left: 100px;float: left;">
        <img id="thumbs-pic" href="#" style="background-color: transparent;" height="80px" width="80px" src="images/thumbsUp.jpg"
             onclick="thumbsUp()">
        <br>
        <span id="thumbs-number"></span>
    </div>
    <div style="height:100px;width:100px;margin-top: 30px;margin-left: 100px;float: left;">
        <img id="collect-pic" href="#" style="background-color: transparent;" height="80px" width="80px" src="images/collect.jpg"
             onclick="collection()">
        <span id="collect"></span>
    </div>
    <div id="follow" style="height:100px;width:100px;margin-top: 30px;margin-left: 100px;float: left;">
        <button id="follow-author" style="height: 80px;width: 80px;" onclick="followAuthor()"></button>
    </div>
    <div style="height:100px;width:100px;margin-top: 30px;margin-left: 100px;float: left;">
        <button id="back" style="height: 80px;width: 80px;" onclick="back()">返回上级页面</button>
    </div>
    <div id="owner-msg" style="height: 100%;width: 250px;float:left;margin-left: 100px;border-left: #c0a16b solid;">
    </div>
</div>
<div id="comment"
     style="padding-top:5px;padding-left:5px;padding-right:5px;width:80%;margin-left:10%;margin-bottom:50px;margin-top: 50px;border: #cccccc solid;">
    <div id="comment_title" style="margin-top: 10px;margin-left: 10px;">评论区</div>
    <div id="comment_content" style="margin-top: 40px;margin-left: 10px;"></div>
    <div id="comment_create" style="margin-top: 40px;margin-left: 10px;"></div>
</div>



</body>
<script>

    $(document).ready(init());

    function init() {
        getOwnerMessage();
        getContent();
        getFollowButton();
    }

    function getFollowButton() {
        var authorId = Cookies.get("ownerId");
        var token = JSON.parse(Cookies.get("token"));
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "follower/" + authorId + "?token=" + token.token.token,
            success: function (result) {
                if (result.success == true) {
                    document.getElementById("follow-author").innerText = "取消关注";
                } else {
                    if(result.message == "被关注对象是自己"){
                        document.getElementById("follow-author").innerText = "嫑关注自己";
                        document.getElementById("follow-author").setAttribute("disabled",true);
                    }else{
                        document.getElementById("follow-author").innerText = "关注作者";
                    }
                }
            }
        });
    }

    function getOwnerMessage() {
        var blogId = Cookies.get("blogId");
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "blog/" + blogId + "/owner",
            success: function (result) {
                Cookies.set("ownerId", result.data.id);
                document.getElementById("owner-msg").innerHTML =
                    '<br><span href="" style="margin-left:15px;">作者：' + result.data.userName + '</span><br><br>'
                    + '<span style="margin-left:15px;">粉丝数：' + result.data.fans + '</span><br><br>'
                    + '<span style="margin-left:15px;">文章数：' + result.data.blogs + '</span>';
            }
        });
    }

    function thumbsUp() {
        var blogId = Cookies.get("blogId");
        var token = JSON.parse(Cookies.get("token"));
        $.ajax({
            type: "PUT",
            contentType: "application/json",
            url: "count/" + blogId + "/laud?token=" + token.token.token,
            success:function (result) {
                if(result.success == true){
                    var a=document.getElementById("thumbs-number").innerHTML;
                    var b=(a-0)+1;
                    document.getElementById("thumbs-number").innerHTML=b;
                }
            }
        })
    }

    function back(){
        window.history.back();
    }

    function followAuthor() {
        var authorId = Cookies.get("ownerId");
        var token = JSON.parse(Cookies.get("token"));
        if (document.getElementById("follow-author").innerText == "关注作者") {
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "follower/" + authorId + "?token=" + token.token.token + "&messagePush=true",
                success: function (result) {
                    if (result.success == true) {
                        document.getElementById("follow-author").innerText = "取消关注";
                    }
                }
            });
        } else {
            $.ajax({
                type: "DELETE",
                contentType: "application/json",
                url: "follower/" + authorId + "?token=" + token.token.token,
                success: function (result) {
                    if (result.success == true) {
                        document.getElementById("follow-author").innerText = "关注作者";
                    }
                }
            });
        }

    }

    function collection() {
        var blogId = Cookies.get("blogId");
        var token = JSON.parse(Cookies.get("token"));
        if (document.getElementById("collect").innerText == "收藏") {
            $.ajax({
                type: "PUT",
                contentType: "application/json",
                url: "blog/collection/" + blogId + "?token=" + token.token.token,
                dataType: "json",
                success: function (result) {
                    if (result.success == true) {
                        document.getElementById("collect").innerHTML = "取消收藏";
                    }
                }
            })
        } else if (document.getElementById("collect").innerText == "取消收藏") {
            $.ajax({
                type: "DELETE",
                contentType: "application/json",
                url: "blog/collection/" + blogId + "?token=" + token.token.token,
                dataType: "json",
                success: function (result) {
                    if (result.success == true) {
                        document.getElementById("collect").innerHTML = "收藏";
                    }
                }
            })
        }
    }

    function getContent(){
        var blogId = Cookies.get("blogId");
        $.ajax({
            type : "GET",
            contentType: "application/json",
            url: "blog/" + blogId,
            dataType: "json",
            success: function(result){
                var title = document.createElement("div");
                var readCount = document.createElement("div");
                var content = document.createElement("div");

                title.innerHTML = "<h1 align='center'>" + result.data.title + "</h1>";
                readCount.innerHTML = "<span style='margin-right: 15px;'>" + getTime(result.data.createTime) + "</span><span>" + "阅读量：" + result.data.readCount + "</span><br><br>";
                content.innerHTML = result.data.content + "<br><br><br><br>";


                document.getElementById("title").appendChild(title);
                document.getElementById("readCount").appendChild(readCount);
                document.getElementById("content").appendChild(content);
                document.getElementById("thumbs-number").innerHTML = result.data.likeCount;
                getComment();
                getCollection();

                var loginUserName = Cookies.get("loginUserName");
                var userName = document.createElement("div");
                userName.innerHTML = '<a href="login">登录</a>\n' +
                    '    <a href="signUp">注册</a>';
                userName.style.width = "30%";
                userName.style.marginRight = "100px";
                userName.style.height = "25px";
                userName.style.cssFloat = "right";
                userName.align = "right";

                if (loginUserName != null) {
                    userName.innerHTML = Cookies.get("loginUserName");
                }
                document.getElementById("userName").appendChild(userName);
            }
        });
    }

    function getCollection() {
        var blogId = Cookies.get("blogId");
        var token = JSON.parse(Cookies.get("token"));
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "blog/collected/" + blogId + "?token=" + token.token.token,
            success: function (result) {
                if (result.data == true) {
                    document.getElementById("collect").innerHTML = "取消收藏";
                } else {
                    document.getElementById("collect").innerHTML = "收藏";
                }
            }
        })
    }

    function getComment() {
        var blogId = Cookies.get("blogId");
        var comment_content = document.createElement("div");
        var comment_create = document.createElement("div");
        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: "comment/" + blogId,
            success: function (comments) {
                if (comments.data.length == 0) {
                    comment_content.innerHTML = "暂无评论<br>";
                } else {
                    for (var i = 0; i < comments.data.length; i++) {
                        if (comments.data[i].attachedUserName == null) {
                            comment_content.innerHTML += '<span>' + comments.data[i].userName + ":";
                        } else {
                            comments.innerHTML += '<span>' + comments.data[i].userName + "回复" + comments.data[i].attachedUserName + ":";
                        }
                        comment_content.innerHTML += comments.data[i].content + "</span><br>";
                    }
                }
                comment_create.innerHTML += '<br><br><br><div style="margin-right: 20px;"><textarea id="newComment" placeholder="发表评论" style="width:100%;height: 80px;"></textarea>' + "</div>";
                comment_create.innerHTML += '<br><br><div align="center"><button style="height: 40px;width: 80px;" onclick="submitComment()">发表评论</button></div><br><br>';
            }
        });
        document.getElementById("comment_content").appendChild(comment_content);
        document.getElementById("comment_create").appendChild(comment_create);
    }

    function appendComment(content) {
        var append = document.createElement("span");
        append.innerHTML = '<span>' + Cookies.get("loginUserName") + ":" + content + '</span><br>';
        document.getElementById("comment_content").appendChild(append);
    }

    function submitComment() {
        var comment = $("#newComment").val();
        var data = {"blogId": Cookies.get("blogId"), "content": comment};
        var token = JSON.parse(Cookies.get("token"));
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "comment?token=" + token.token.token,
            dataType: "json",
            data: JSON.stringify(data),
            success: function (result) {
                if (result.success == true) {
                    appendComment(comment);
                    document.getElementById('newComment').value = "";
                }
            }
        });
    }


    function getTime(data) {
        var date = new Date(data);
        var Y = date.getFullYear() + '-';
        var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
        var D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + ' ';
        var h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
        var m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
        var s = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds());
        return Y + M + D + h + m + s
    }
</script>
</html>
