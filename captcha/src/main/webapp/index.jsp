<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="js/functions.js"></script>


    <title>测试页面</title>
    <script type="text/javascript">
        $(function () {
            $('#kaptchaImage').click(function () {//生成验证码
                $(this).hide().attr('src', './kaptcha/getKaptchaImage.do?' + Math.floor(Math.random() * 100)).fadeIn();
                event.cancelBubble = true;
            });
            $('button').click(function () {//验证码校验
                var randCode = $("#kaptcha").val();
                if (randCode) {
                    $.ajax('./kaptcha/checkRandCode.do?' + Math.floor(Math.random() * 100), {
                        type: "POST",
                        data: {"randCode": randCode},
                        success: function (dataObj) {
                            alert(dataObj);
                        }
                    })
                } else {
                    alert("kaptcha is not empty.")
                }
            });
        });


        window.onbeforeunload = function () {
            //关闭窗口时自动退出
            if (event.clientX > 360 && event.clientY < 0 || event.altKey) {
                alert(parent.document.location);
            }
        };


        function changeCode() {
            $('#kaptchaImage').hide().attr('src', './kaptcha/getKaptchaImage.do?' + Math.floor(Math.random() * 100)).fadeIn();
            event.cancelBubble = true;
        }
    </script>
</head>
<body>

<div class="chknumber">
    <label>验证码:
        <input name="kaptcha" type="text" id="kaptcha" maxlength="4" class="chknumber_input"/>
    </label>
    <br/>
    <img src="./kaptcha/getKaptchaImage.do" id="kaptchaImage" style="margin-bottom: -3px"/>
    <a href="#" onclick="changeCode()">看不清?换一张</a>
    <br/>
    <button>提交</button>
</div>
</body>
</html>