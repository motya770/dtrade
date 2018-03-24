<!DOCTYPE html>
<html>

<head>

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
        loadCSS( "/theme/app/css/fonts.min.css?ver=1.0.0", false, "all" ); // Loading fonts, if the site is located in a subfolder
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

    <script src="/bower_components/angular/angular.min.js" type="text/javascript"></script>
    <script src="/bower_components/angular-resource/angular-resource.min.js" type="text/javascript"></script>

    <script src="/content/js/app.js"></script>
    <script src="/content/js/app/account.controller.js"></script>
    <script src="/content/js/app/account.service.js"></script>
    <script src="/content/js/app/balanceactivity.service.js"></script>
    <script src="/content/js/app/balanceactivity.controller.js"></script>

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
                <li>
                    <a href="#">Бриллианты</a>
                </li>
                <li>
                    <a href="#">Текущие сделки</a>
                </li>
                <li class="active">
                    <a href="#">Мой аккаунт</a>
                </li>
            </ul>
        </div>
    </nav>
    <main class="content">
        <div class="cabtop-info">
            <div class="cabtop-info__inner">
                <div class="cabtop-info__content">
                    <span class="cabtop-info__ico"></span>
                    <div class="cabtop-info__text">
                        <h3>Портфель акций</h3>
                        <p><span class="accent">34 акции</span> на общую сумму <span class="accent">145 000 руб.</span></p>
                    </div>
                </div>
            </div>
        </div>
        <div class="def-cab-page">
            <div class="def-cab-page__inner">
                <div class="def-cab-page__content">
                    <h2 class="def-cab-page__heading">Мои данные</h2>
                    <ul class="lk-tabs-heading">
                        <li class="active">
                            <a href="#tab01">История изменения баланса</a>
                        </li>
                        <li>
                            <a href="#tab02">Моя история торгов</a>
                        </li>
                        <li>
                            <a href="#tab03">Депозит и вывод денег через карту</a>
                        </li>
                    </ul>

                    <div ng-controller="BalanceActivityController as vm">
                        {{vm.balanceActivities}}
                    </div>
                    <div class="lk-main-tabs">
                        <div class="lk-main-tab" id="tab01">
                            <div class="lk-main-tab__table">
                                <div class="table-container">
                                    <table class="diamont-table">
                                        <thead>
                                        <tr>
                                            <th>№</th>
                                            <th>Дата</th>
                                            <th>Сумма, руб</th>
                                            <th>Вид операции</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td>1</td>
                                            <td>11.10.2017</td>
                                            <td>10000</td>
                                            <td>Покупка</td>
                                        </tr>
                                        <tr>
                                            <td>2</td>
                                            <td>09.10.2017</td>
                                            <td>16000</td>
                                            <td>Продажа</td>
                                        </tr>
                                        <tr>
                                            <td>3</td>
                                            <td>07.10.2017</td>
                                            <td>16000</td>
                                            <td>Продажа</td>
                                        </tr>
                                        <tr>
                                            <td>4</td>
                                            <td>11.10.2017</td>
                                            <td>10000</td>
                                            <td>Покупка</td>
                                        </tr>
                                        <tr>
                                            <td>5</td>
                                            <td>09.10.2017</td>
                                            <td>16000</td>
                                            <td>Продажа</td>
                                        </tr>
                                        <tr>
                                            <td>6</td>
                                            <td>07.10.2017</td>
                                            <td>16000</td>
                                            <td>Продажа</td>
                                        </tr>
                                        <tr>
                                            <td>7</td>
                                            <td>11.10.2017</td>
                                            <td>10000</td>
                                            <td>Покупка</td>
                                        </tr>
                                        <tr>
                                            <td>8</td>
                                            <td>09.10.2017</td>
                                            <td>16000</td>
                                            <td>Продажа</td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="lk-main-tab__info">
                                <div class="lk-stat-info">
                                    <div class="lk-stat-info__elem lk-stat-info__elem--calendar">
                                        <h3>Декабрь 2017</h3>
                                        <p>Последние изменения — 17 декабря 2017</p>
                                    </div>
                                    <div class="lk-stat-info__elem lk-stat-info__elem--graph">
                                        <h3>Доход 20000 руб</h3>
                                        <h3>Потери 5000 руб</h3>
                                    </div>
                                    <div class="lk-stat-info__elem lk-stat-info__elem--reverse">
                                        <h3>Продажа акций на сумму $10000</h3>
                                        <h3>Покупка акций на сумму $20000</h3>
                                    </div>
                                </div>
                                <div class="lk-stat-charts">
                                    <div class="chart">
                                        <div class="donut-chart" data-percent="0.75" data-val="20000" data-title="доход"></div>
                                    </div>
                                    <div class="chart chart--red">
                                        <div class="donut-chart" data-percent="0.75" data-val="5000" data-title="расход"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="lk-main-tab" id="tab02">
                            <div class="lk-main-tab__table">
                                <div class="table-container">
                                    <table class="diamont-table">
                                        <thead>
                                        <tr>
                                            <th>№</th>
                                            <th>Дата</th>
                                            <th>Акция</th>
                                            <th>№ Акции</th>
                                            <th>Цена</th>
                                            <th>Сумма</th>
                                            <th>Баланс</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td>1</td>
                                            <td>11.10.2017</td>
                                            <td>Brill I</td>
                                            <td>10</td>
                                            <td>9000</td>
                                            <td>90000</td>
                                            <td>130000</td>
                                        </tr>
                                        <tr>
                                            <td>2</td>
                                            <td>09.10.2017</td>
                                            <td>Brill</td>
                                            <td>20</td>
                                            <td>10000</td>
                                            <td>200000</td>
                                            <td>222000</td>
                                        </tr>
                                        <tr>
                                            <td>3</td>
                                            <td>07.10.2017</td>
                                            <td>Brill III</td>
                                            <td>15</td>
                                            <td>20000</td>
                                            <td>300000</td>
                                            <td>470000</td>
                                        </tr>
                                        <tr>
                                            <td>1</td>
                                            <td>11.10.2017</td>
                                            <td>Brill I</td>
                                            <td>10</td>
                                            <td>9000</td>
                                            <td>90000</td>
                                            <td>130000</td>
                                        </tr>
                                        <tr>
                                            <td>2</td>
                                            <td>09.10.2017</td>
                                            <td>Brill</td>
                                            <td>20</td>
                                            <td>10000</td>
                                            <td>200000</td>
                                            <td>222000</td>
                                        </tr>
                                        <tr>
                                            <td>3</td>
                                            <td>07.10.2017</td>
                                            <td>Brill III</td>
                                            <td>15</td>
                                            <td>20000</td>
                                            <td>300000</td>
                                            <td>470000</td>
                                        </tr>
                                        <tr>
                                            <td>1</td>
                                            <td>11.10.2017</td>
                                            <td>Brill I</td>
                                            <td>10</td>
                                            <td>9000</td>
                                            <td>90000</td>
                                            <td>130000</td>
                                        </tr>
                                        <tr>
                                            <td>2</td>
                                            <td>09.10.2017</td>
                                            <td>Brill</td>
                                            <td>20</td>
                                            <td>10000</td>
                                            <td>200000</td>
                                            <td>222000</td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="lk-main-tab__info">
                                <div class="lk-stat-info">
                                    <div class="lk-stat-info__elem lk-stat-info__elem--calendar">
                                        <h3>Декабрь 2017</h3>
                                        <p>Последние изменения — 17 декабря 2017</p>
                                    </div>
                                    <div class="lk-stat-info__elem lk-stat-info__elem--graph">
                                        <h3>Доход 20000 руб</h3>
                                        <h3>Потери 5000 руб</h3>
                                    </div>
                                    <div class="lk-stat-info__elem lk-stat-info__elem--reverse">
                                        <h3>Продажа акций на сумму $10000</h3>
                                        <h3>Покупка акций на сумму $20000</h3>
                                    </div>
                                </div>
                                <div class="lk-stat-charts">
                                    <div class="chart">
                                        <div class="donut-chart" data-percent="0.75" data-val="20000" data-title="доход"></div>
                                    </div>
                                    <div class="chart chart--red">
                                        <div class="donut-chart" data-percent="0.75" data-val="5000" data-title="расход"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </>
            </div>
        </div>
    </main>
    <div class="footer">
        <div class="footer__inner">
            <div class="footer__content">
                <p class="footer__copy">Инвестиционное бюро бриллиантов</p>
                <img src="img/cards.png" alt="cards" class="footer__card">
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
