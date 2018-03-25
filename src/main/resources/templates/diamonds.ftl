<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">

    <title>Title</title>
    <meta name="description" content="">

    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <!-- Template Basic Images Start -->
    <meta property="og:image" content="path/to/image.jpg">
    <link rel="shortcut icon" href="img/favicon/favicon.ico" type="image/x-icon">
    <link rel="apple-touch-icon" href="img/favicon/apple-touch-icon.png">
    <link rel="apple-touch-icon" sizes="72x72" href="img/favicon/apple-touch-icon-72x72.png">
    <link rel="apple-touch-icon" sizes="114x114" href="img/favicon/apple-touch-icon-114x114.png">
    <!-- Template Basic Images End -->

    <!-- Header CSS (first screen styles from header.min.css) - inserted in the build of the project -->
    <style>@@include('header.min.css')</style>

    <!-- Load CSS, CSS Localstorage & WebFonts Main Function -->
    <script>!function(e){"use strict";function t(e,t,n){e.addEventListener?e.addEventListener(t,n,!1):e.attachEvent&&e.attachEvent("on"+t,n)};function n(t,n){return e.localStorage&&localStorage[t+"_content"]&&localStorage[t+"_file"]===n};function a(t,a){if(e.localStorage&&e.XMLHttpRequest)n(t,a)?o(localStorage[t+"_content"]):l(t,a);else{var s=r.createElement("link");s.href=a,s.id=t,s.rel="stylesheet",s.type="text/css",r.getElementsByTagName("head")[0].appendChild(s),r.cookie=t}}function l(e,t){var n=new XMLHttpRequest;n.open("GET",t,!0),n.onreadystatechange=function(){4===n.readyState&&200===n.status&&(o(n.responseText),localStorage[e+"_content"]=n.responseText,localStorage[e+"_file"]=t)},n.send()}function o(e){var t=r.createElement("style");t.setAttribute("type","text/css"),r.getElementsByTagName("head")[0].appendChild(t),t.styleSheet?t.styleSheet.cssText=e:t.innerHTML=e}var r=e.document;e.loadCSS=function(e,t,n){var a,l=r.createElement("link");if(t)a=t;else{var o;o=r.querySelectorAll?r.querySelectorAll("style,link[rel=stylesheet],script"):(r.body||r.getElementsByTagName("head")[0]).childNodes,a=o[o.length-1]}var s=r.styleSheets;l.rel="stylesheet",l.href=e,l.media="only x",a.parentNode.insertBefore(l,t?a:a.nextSibling);var c=function(e){for(var t=l.href,n=s.length;n--;)if(s[n].href===t)return e();setTimeout(function(){c(e)})};return l.onloadcssdefined=c,c(function(){l.media=n||"all"}),l},e.loadLocalStorageCSS=function(l,o){n(l,o)||r.cookie.indexOf(l)>-1?a(l,o):t(e,"load",function(){a(l,o)})}}(this);</script>

    <!-- Load Fonts CSS Start -->
    <script>
        loadCSS( "css/fonts.min.css?ver=1.0.0", false, "all" ); // Loading fonts, if the site is located in a subfolder
        // loadLocalStorageCSS( "webfonts", "css/fonts.min.css?ver=1.0.0" ); // Loading fonts, if the site is at the root
    </script>
    <!-- Load Fonts CSS End -->

    <!-- Load Custom CSS Start -->
    <!--<Deject>--><script>loadCSS( "header.min.css?ver=1.0.0", false, "all" );</script><!--</Deject>-->
    <script>loadCSS( "css/main.min.css?ver=1.0.0", false, "all" );</script>
    <!-- Load Custom CSS End -->

    <!-- Load Custom CSS Compiled without JS Start -->
    <noscript>
        <link rel="stylesheet" href="css/fonts.min.css">
        <link rel="stylesheet" href="css/main.min.css">
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
                    <h2 class="def-cab-page__heading">Акции бриллиантов</h2>
                    <div class="table-container">
                        <table class="diamont-table">
                            <thead>
                            <tr>
                                <th>Наименование</th>
                                <th>Стартовый капитал</th>
                                <th>Количество</th>
                                <th>Тип</th>
                                <th>Статус</th>
                                <th>Караты</th>
                                <th>Чистота</th>
                                <th>Σ акций</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>Brilliant Elizaveta II<br>ISIN: DE000520000</td>
                                <td>$10000</td>
                                <td>20</td>
                                <td>Radian</td>
                                <td>Открыт</td>
                                <td>2</td>
                                <td>1</td>
                                <td>$28970</td>
                                <td>
                                    <a href="#" data-open="1">Узнать подробности</a>
                                </td>
                            </tr>
                            <tr class="nopadding" data-show="1">
                                <td colspan="9">
                                    <h2>Динамика стоимости акций 2017</h2>
                                    <img src="img/graph01.png" alt="graph">
                                    <h2>Условия инвестироания в акции</h2>
                                    <p>Минимальная сумма вложений для первой покупки акций  - 10 акций 1 000$</p>
                                    <p>Минимальная сумма вложений для последующих покупок - 10 акций 1 000$</p>
                                    <h2>Как инвестировать</h2>
                                    <p>Дистанционно через Личный кабинет</p>
                                </td>
                            </tr>
                            <tr>
                                <td>Brilliant Elizaveta II<br>ISIN: DE000520000</td>
                                <td>$11000</td>
                                <td>30</td>
                                <td>Radian</td>
                                <td>Закрыт</td>
                                <td>2</td>
                                <td>1</td>
                                <td>$38950</td>
                                <td>
                                    <a href="#" data-open="2">Узнать подробности</a>
                                </td>
                            </tr>
                            <tr class="nopadding" data-show="2">
                                <td colspan="9">
                                    <h2>Динамика стоимости акций 2017</h2>
                                    <img src="img/graph01.png" alt="graph">
                                    <h2>Условия инвестироания в акции</h2>
                                    <p>Минимальная сумма вложений для первой покупки акций  - 10 акций 1 000$</p>
                                    <p>Минимальная сумма вложений для последующих покупок - 10 акций 1 000$</p>
                                    <h2>Как инвестировать</h2>
                                    <p>Дистанционно через Личный кабинет</p>
                                </td>
                            </tr>
                            <tr>
                                <td>Brilliant Elizaveta II<br>ISIN: DE000520000</td>
                                <td>$11000</td>
                                <td>40</td>
                                <td>Radian</td>
                                <td>Открыт</td>
                                <td>2</td>
                                <td>1</td>
                                <td>$18970</td>
                                <td>
                                    <a href="#" data-open="3">Узнать подробности</a>
                                </td>
                            </tr>
                            <tr class="nopadding" data-show="3">
                                <td colspan="9">
                                    <h2>Динамика стоимости акций 2017</h2>
                                    <img src="img/graph01.png" alt="graph">
                                    <h2>Условия инвестироания в акции</h2>
                                    <p>Минимальная сумма вложений для первой покупки акций  - 10 акций 1 000$</p>
                                    <p>Минимальная сумма вложений для последующих покупок - 10 акций 1 000$</p>
                                    <h2>Как инвестировать</h2>
                                    <p>Дистанционно через Личный кабинет</p>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
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
    {"src" : "js/libs.min.js", "async" : false},
    {"src" : "js/common.js", "async" : false}
]};!function(t,n,r){"use strict";var c=function(t){if("[object Array]"!==Object.prototype.toString.call(t))return!1;for(var r=0;r<t.length;r++){var c=n.createElement("script"),e=t[r];c.src=e.src,c.async=e.async,n.body.appendChild(c)}return!0};t.addEventListener?t.addEventListener("load",function(){c(r.scripts);},!1):t.attachEvent?t.attachEvent("onload",function(){c(r.scripts)}):t.onload=function(){c(r.scripts)}}(window,document,scr);
</script>
<!-- Optimized loading JS End -->

</body>
</html>
