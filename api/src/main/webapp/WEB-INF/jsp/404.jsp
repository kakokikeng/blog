<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>404 Not Find:(</title>
    <style>
        ::-moz-selection {
            background: #b3d4fc;
            text-shadow: none;
        }

        ::selection {
            background: #b3d4fc;
            text-shadow: none;
        }

        html {
            padding: 30px 10px;
            font-size: 20px;
            line-height: 1.4;
            color: #737373;
            background: #f0f0f0;
            -webkit-text-size-adjust: 100%;
            -ms-text-size-adjust: 100%;
        }

        html,
        input {
            font-family: "Helvetica Neue", Helvetica, Arial, sans-serif;
        }

        body {
            max-width: 500px;
            _width: 500px;
            padding: 30px 20px 50px;
            border: 1px solid #b3b3b3;
            border-radius: 4px;
            margin: 0 auto;
            box-shadow: 0 1px 10px #a7a7a7, inset 0 1px 0 #fff;
            background: #fcfcfc;
        }

        h1 {
            margin: 0 10px;
            font-size: 50px;
            text-align: center;
        }

        h1 span {
            color: #bbb;
        }

        h3 {
            margin: 1.5em 0 0.5em;
        }

        p {
            margin: 1em 0;
        }

        ul {
            padding: 0 0 0 40px;
            margin: 1em 0;
        }

        .container {
            max-width: 380px;
            _width: 380px;
            margin: 16% auto;
            text-align: center;
        }

        /* google search */

        #goog-fixurl ul {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        #goog-fixurl form {
            margin: 0;
        }

        #goog-wm-qt,
        #goog-wm-sb {
            border: 1px solid #bbb;
            font-size: 16px;
            line-height: normal;
            vertical-align: top;
            color: #444;
            border-radius: 2px;
        }

        #goog-wm-qt {
            width: 220px;
            height: 20px;
            padding: 5px;
            margin: 5px 10px 0 0;
            box-shadow: inset 0 1px 1px #ccc;
        }

        #goog-wm-sb {
            display: inline-block;
            height: 32px;
            padding: 0 10px;
            margin: 5px 0 0;
            white-space: nowrap;
            cursor: pointer;
            background-color: #f5f5f5;
            background-image: -webkit-linear-gradient(rgba(255,255,255,0), #f1f1f1);
            background-image: -moz-linear-gradient(rgba(255,255,255,0), #f1f1f1);
            background-image: -ms-linear-gradient(rgba(255,255,255,0), #f1f1f1);
            background-image: -o-linear-gradient(rgba(255,255,255,0), #f1f1f1);
            -webkit-appearance: none;
            -moz-appearance: none;
            appearance: none;
            *overflow: visible;
            *display: inline;
            *zoom: 1;
        }

        #goog-wm-sb:hover,
        #goog-wm-sb:focus {
            border-color: #aaa;
            box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
            background-color: #f8f8f8;
        }

        #goog-wm-qt:hover,
        #goog-wm-qt:focus {
            border-color: #105cb6;
            outline: 0;
            color: #222;
        }

        input::-moz-focus-inner {
            padding: 0;
            border: 0;
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
<div class="container">
    <h1>404 Not Find<span>:(</span></h1>
    <p>对不起，您访问的页面不存在~</p>
    <p>请输入正确的地址</p>
    <p><em id="num">3</em>秒后，自动跳转到上一页</p>
    <script>
        var i =3;
        function djs() {
            if(i==0){
                window.history.back();
            }
            document.getElementById("num").innerText=i--;
            setTimeout("djs()",1000);
        }
        djs();
    </script>
</div>
</body>
</html>