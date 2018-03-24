<!DOCTYPE html>
<html ng-app="diamondApp">
<head>
    <link rel="stylesheet" href= "/content/css/material-input.css" />
    <link rel="stylesheet" href= "/bower_components/angular-material/angular-material.min.css"/>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

    <script src="/bower_components/angular/angular.js"></script>
    <script src="/bower_components/angular-animate/angular-animate.min.js"></script>
    <script src="/bower_components/angular-aria/angular-aria.min.js"></script>
    <script src="/bower_components/angular-messages/angular-messages.min.js"></script>
    <script src="/bower_components/angular-material/angular-material.min.js"></script>
    <script src="/content/js/registration.js" ></script>

    <meta charset="utf-8">

    <title>Title</title>
    <meta name="description" content="">

    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <!-- Template Basic Images Start -->
    <meta property="og:image" content="/theme/app/path/to/image.jpg">
    <link rel="shortcut icon" href="/theme/app/img/favicon/favicon.ico" type="image/x-icon">
    <link rel="apple-touch-icon" href="/theme/app/img/favicon/apple-touch-icon.png">
    <link rel="apple-touch-icon" sizes="72x72" href="/theme/app/img/favicon/apple-touch-icon-72x72.png">
    <link rel="apple-touch-icon" sizes="114x114" href="/theme/app/img/favicon/apple-touch-icon-114x114.png">
    <!-- Template Basic Images End -->

    <!-- Header CSS (first screen styles from header.min.css) - inserted in the build of the project -->
    <style>@@include('/theme/app/header.min.css')</style>

    <!-- Load CSS, CSS Localstorage & WebFonts Main Function -->
    <script>!function(e){"use strict";function t(e,t,n){e.addEventListener?e.addEventListener(t,n,!1):e.attachEvent&&e.attachEvent("on"+t,n)};function n(t,n){return e.localStorage&&localStorage[t+"_content"]&&localStorage[t+"_file"]===n};function a(t,a){if(e.localStorage&&e.XMLHttpRequest)n(t,a)?o(localStorage[t+"_content"]):l(t,a);else{var s=r.createElement("link");s.href=a,s.id=t,s.rel="stylesheet",s.type="text/css",r.getElementsByTagName("head")[0].appendChild(s),r.cookie=t}}function l(e,t){var n=new XMLHttpRequest;n.open("GET",t,!0),n.onreadystatechange=function(){4===n.readyState&&200===n.status&&(o(n.responseText),localStorage[e+"_content"]=n.responseText,localStorage[e+"_file"]=t)},n.send()}function o(e){var t=r.createElement("style");t.setAttribute("type","text/css"),r.getElementsByTagName("head")[0].appendChild(t),t.styleSheet?t.styleSheet.cssText=e:t.innerHTML=e}var r=e.document;e.loadCSS=function(e,t,n){var a,l=r.createElement("link");if(t)a=t;else{var o;o=r.querySelectorAll?r.querySelectorAll("style,link[rel=stylesheet],script"):(r.body||r.getElementsByTagName("head")[0]).childNodes,a=o[o.length-1]}var s=r.styleSheets;l.rel="stylesheet",l.href=e,l.media="only x",a.parentNode.insertBefore(l,t?a:a.nextSibling);var c=function(e){for(var t=l.href,n=s.length;n--;)if(s[n].href===t)return e();setTimeout(function(){c(e)})};return l.onloadcssdefined=c,c(function(){l.media=n||"all"}),l},e.loadLocalStorageCSS=function(l,o){n(l,o)||r.cookie.indexOf(l)>-1?a(l,o):t(e,"load",function(){a(l,o)})}}(this);</script>

    <!-- Load Fonts CSS Start -->
    <script>
        loadCSS( "/theme/app/fonts.min.css?ver=1.0.0", false, "all" ); // Loading fonts, if the site is located in a subfolder
        // loadLocalStorageCSS( "webfonts", "css/fonts.min.css?ver=1.0.0" ); // Loading fonts, if the site is at the root
    </script>
    <!-- Load Fonts CSS End -->

    <!-- Load Custom CSS Start -->
    <!--<Deject>--><script>loadCSS( "/theme/app/header.min.css?ver=1.0.0", false, "all" );</script><!--</Deject>-->
    <script>loadCSS( "/theme/app/css/main.min.css?ver=1.0.0", false, "all" );</script>
    <!-- Load Custom CSS End -->

    <!-- Load Custom CSS Compiled without JS Start -->
    <noscript>
        <link rel="stylesheet" href="/theme/app/css/fonts.min.css">
        <link rel="stylesheet" href="/theme/app/css/main.min.css">
    </noscript>
    <!-- Load Custom CSS Compiled without JS End -->

    <!-- Custom Browsers Color Start -->
    <!-- Chrome, Firefox OS and Opera -->
    <meta name="theme-color" content="#000">
    <!-- Windows Phone -->
    <meta name="msapplication-navbutton-color" content="#000">
    <!-- iOS Safari -->
    <meta name="apple-mobile-web-app-status-bar-style" content="#000">
    <!-- Custom Browsers Color End -->

</head>

<body class="page">

<!-- Custom HTML -->
<div class="site">
    <header class="header header--page">
        <div class="header__inner">
            <div class="header__logo">
                <a href="/">Платформа для торговли<br>финансовыми акциями бриллиантов</a>
            </div>
            <div class="header__right">
                <a href="#" class="btn btn--darken">Выход</a>
            </div>
        </div>
    </header>
    <nav class="subnav">
        <div class="subnav__inner">
            <ul class="subnav__list">
                <li class="active">
                    <a href="#">Бриллианты</a>
                </li>
                <li>
                    <a href="#">Текущие сделки</a>
                </li>
                <li>
                    <a href="#">Мой аккаунт</a>
                </li>
            </ul>
        </div>
    </nav>
    <main class="content">
        <div class="cabtop-info">
            <div class="cabtop-info__inner">

            </div>
        </div>
        <div class="def-cab-page">
            <div class="def-cab-page__inner">
                <div class="def-cab-page__content">
                    <h2 class="def-cab-page__heading">Вход</h2>
                    <div class="table-container">
                        <div style="width: 400px; margin-left: 8px; margin-bottom: 10px;">
                            <form action="/login" method="post">
                                <md-input-container class="md-block">
                                    <!-- Use floating placeholder instead of label -->
                                    <md-icon class="material-icons md-light md-48"> email </md-icon>
                                    <md-icon md-svg-src="img/icons/ic_email_24px.svg" class="username"></md-icon>
                                    <input type="email" name="username" placeholder="Email (required)" ng-required="true">
                                </md-input-container>

                                <md-input-container md-no-float="" class="md-block">
                                    <label for="password">Password</label>
                                    <input type="password" name="password"
                                           ng-minlength="8" required />
                                    <span class="help-block"
                                          ng-show="newPasswordForm.newPassword.$dirty && newPasswordForm.newPassword.$invalid">
                                            Please enter a new password, it must be at least 6 characters long.
                                    </span>
                                </md-input-container>

                                <!--
                                <input type="text" name="username" /><br/>
                                <input type="password" name="password"><br/>
                                -->

                                <md-button type="submit" class="md-raised md-primary">Primary</md-button>
                                <input type="submit" value="Login"/>
                            </form>
                        </div>

                        <div style="width: 400px;">
                    <h2 class="def-cab-page__heading">Регистрация</h2>
                        <div ng-controller="RegistrationController as vm" style="width:500px;" layout="column" layout-padding="" ng-cloak="" class="material-input", class="input-demo-Errors, ng-app="MyApp">
                         <br>
                        <md-input-container md-no-float="" class="md-block">

                            <md-icon class="material-icons md-light md-48"> phone </md-icon>

                            <md-icon md-svg-src="img/icons/ic_phone_24px.svg"></md-icon>
                            <input ng-model="account.phone" type="text" placeholder="Phone Number(Full format)">
                        </md-input-container>
                        <md-input-container class="md-block">
                            <!-- Use floating placeholder instead of label -->
                            <md-icon class="material-icons md-light md-48"> email </md-icon>
                            <md-icon md-svg-src="img/icons/ic_email_24px.svg" class="email"></md-icon>
                            <input ng-model="account.email" type="email" placeholder="Email (required)" ng-required="true">
                        </md-input-container>
                        <form name="newPasswordForm" role="form" ng-submit="newPasswordForm.$valid && ok()" novalidate>


                        <md-input-container md-no-float="" class="md-block">
                            <label for="newPassword">New Password</label>
                            <input type="password" name="newPassword" ng-model="account.password.new"
                                   ng-minlength="6" required />
                            <span class="help-block"
                                  ng-show="newPasswordForm.newPassword.$dirty && newPasswordForm.newPassword.$invalid">
                               Please enter a new password, it must be at least 6 characters long.
                        </span>
                        </md-input-container>
                        <md-input-container md-no-float="" class="md-block">
                            <label for="newPasswordConfirm">Confirm New Password</label>
                            <input type="password" name="newPasswordConfirm"
                                   ng-model="account.password.confirm" ng-minlength="6"
                                   value-matches="account.password.new" required />
                            <span class="help-block"
                                  ng-show="newPasswordForm.newPasswordConfirm.$dirty && newPasswordForm.newPasswordConfirm.$invalid">
                                 Please enter the same password again to confirm.
                        </span>
                        </md-input-container>
                    </form>
                        <md-input-container class="md-block">
                        <md-checkbox name="tos" ng-model="project.tos" required="">
                            I accept the terms of service.
                        </md-checkbox>
                        <div ng-messages="projectForm.tos.$error" multiple="" md-auto-hide="false">
                            <div ng-message="required">
                                You must accept the terms of service before you can proceed.
                            </div>
                        </div>
                    </md-input-container>
                        <div>
                            <md-button class="md-raised md-primary" type="submit" ng-click="createAccount(account);">Submit</md-button>
                        </div>
                        </md-content>
                    </div>

                    </div>
                </div>
            </div>
        </div>
    </main>

    <div class="footer">
        <div class="footer__inner">
            <div class="footer__content">
                <p class="footer__copy">Инвестиционное бюро бриллиантов</p>
                <img src="/theme/app/img/cards.png" alt="cards" class="footer__card">
            </div>
        </div>
    </div>
</div>

<!-- Optimized loading JS Start -->
<script>var scr = {"scripts":[
    {"src" : "/theme/app/js/libs.min.js", "async" : false},
    {"src" : "/theme/app/js/common.js", "async" : false}
]};!function(t,n,r){"use strict";var c=function(t){if("[object Array]"!==Object.prototype.toString.call(t))return!1;for(var r=0;r<t.length;r++){var c=n.createElement("script"),e=t[r];c.src=e.src,c.async=e.async,n.body.appendChild(c)}return!0};t.addEventListener?t.addEventListener("load",function(){c(r.scripts);},!1):t.attachEvent?t.attachEvent("onload",function(){c(r.scripts)}):t.onload=function(){c(r.scripts)}}(window,document,scr);
</script>
<!-- Optimized loading JS End -->

</body>
</html>



