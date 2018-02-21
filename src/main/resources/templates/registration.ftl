<html>
<body>
<link rel="stylesheet" href="/resources/css/trade/intlTelInput.css" />
<script src="/resources/js/plat/registration.js" ></script>

<script src="//www.google.com/recaptcha/api.js?onload=CaptchaCallback&render=explicit" async defer></script>
<script type="text/javascript">
    var CaptchaCallback = function () {
        grecaptcha.render('DemoRecaptcha', {'sitekey': '6LeIRwUTAAAAAIYGH56PGrgIZj-nyJ8As2_BiiwM'});
        grecaptcha.render('RealRecaptcha', {'sitekey': '6LeIRwUTAAAAAIYGH56PGrgIZj-nyJ8As2_BiiwM'});
    };
</script>

<div>
    <form action="register" method="post">
        <div>
            Email
            <input type="email" name="mail"/>
        </div>
        <div>
            Password
            <input type="password" name="pwd"/>
        </div>
        <div>
            Repeat Password
            <input type="password" name="pwd"/>
        </div>
        <div>
            Country
            <input type="text" name="country"/>
        </div>
        <div>
            Real Or Demo
        </div>
        <div>
            Phone
            <input type="text" name="phone"/>
        </div>

        <input type="submit" value="Register"/>
    </form>
</div>

</body>
</html>