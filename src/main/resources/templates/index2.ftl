<!DOCTYPE html>
<html ng-app="diamondApp">

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
        loadCSS( "/theme/app//css/fonts.min.css?ver=1.0.0", false, "all" ); // Loading fonts, if the site is located in a subfolder
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

    <link rel="stylesheet" href="/bower_components/highcharts-ng/dist/highcharts-ng.css ">

    <script src="/bower_components/highcharts/highstock.js" type="text/javascript"></script>
    <script src="/bower_components/highcharts-ng/dist/highcharts-ng.js" type="text/javascript"></script>
    <script src="/bower_components/less/dist/less.min.js" type="text/javascript"></script>

    <script src="/content/js/app.js"></script>
    <script src="/content/js/app/account.controller.js"></script>
    <script src="/content/js/app/account.service.js"></script>
    <script src="/content/js/app/available.controller.js"></script>
    <script src="/content/js/app/available.service.js"></script>
    <script src="/content/js/app/bidder.controller.js"></script>
    <script src="/content/js/app/bookorder.controller.js"></script>
    <script src="/content/js/app/bookorder.service.js"></script>
    <script src="/content/js/app/chart.controller.js"></script>
    <script src="/content/js/app/register.controller.js"></script>
    <script src="/content/js/app/stock.controller.js"></script>
    <script src="/content/js/app/stock.service.js"></script>
    <!--
    <script src="/content/js/app/mydiamonds.service.js"></script>
    -->
    <script src="/content/js/app/owned.controller.js"></script>
    <script src="/content/js/app/sale.controller.js"></script>
    <script src="/content/js/app/sale.service.js"></script>
    <script src="/content/js/app/owned.service.js"></script>
    <script src="/content/js/app/tradeorder.controller.js"></script>
    <script src="/content/js/app/tradeorder.service.js"></script>
    <script src="/content/js/app/utils.js"></script>


    <!-- Angular Material requires Angular.js Libraries -->
    <script src="/bower_components/angular-animate/angular-animate.js"></script>
    <script src="/bower_components/angular-aria/angular-aria.js"></script>
    <script src="/bower_components/angular-messages/angular-messages.min.js"></script>

    <!-- Angular Material Library -->
    <script src="/bower_components/angular-material/angular-material.min.js"></script>


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
                <a href="#" class="btn btn--darken">Вход</a>
            </div>


        </div>
        <form action="/login" method="post">
            <input type="text" name="username" /><br/>
            <input type="password" name="password"><br/>
            <input type="submit" value="Login"/>
        </form>
    </header>
    <!--
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
    </nav>-->
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
        <div class="sep-cab-page">
            <div class="sep-cab-page__inner">
                <div class="sep-cab-page__content">
                    <div class="sep-cab-page__top">
                        <div class="sep-cab-page__side sep-cab-page__left">
                            <div class="sep-cab-page__heading">
                                <h2>Список бриллиантов</h2>
                                <p>Все акции бриллиантов в работе</p>
                            </div>
                            <form class="searchform">
                                <input type="text" class="searchform__inp" placeholder="Введите имя бриллианта">
                                <button class="searchform__btn">Найти</button>
                            </form>


                            <div class="type-diamond-table">
                                <div class="table-container">
                                    <div ng-controller="AvailableController as vm">
                                      <table class="diamont-table">
                                        <thead>
                                        <tr>
                                            <th>Бриллиант</th>
                                            <th></th>
                                            <th>N</th>
                                            <th>P <span class="inf">?</span></th>
                                            <th>S <span class="inf">?</span></th>
                                        </tr>
                                        </thead>
                                        <tbody>

                                            <tr ng-repeat="diamond in vm.availableDiamonds" ng-click="chooseAvailableDiamond(diamond)">
                                                <td>{{diamond.name}} <small>{{diamond.id}}, {{diamond.diamondType}}, {{diamond.carats}}, {{diamond.clarity}}</small></td>
                                                <td>
                                                    <img src="/theme/app/img/dia1.png" alt="diamond">
                                                </td>
                                                <td>100$</td>
                                                <td>100$</td>
                                                <td>{{diamond.totalStockAmount}}</td>
                                            </tr>
                                        <!--
                                        <tr>
                                            <td>1Brilliant&nbsp;Elizaveta <small>Rad, GIA - WS1, Cr-7</small></td>
                                            <td>
                                                <img src="/theme/app/img/dia1.png" alt="diamond">
                                            </td>
                                            <td>300</td>
                                            <td>10&nbsp;000</td>
                                            <td>12&nbsp;000</td>
                                        </tr>
                                        <tr>
                                            <td>Brilliant Elis <small>Ass, GIA - WS1, Cr-6</small></td>
                                            <td>
                                                <img src="/theme/app/img/dia2.png" alt="diamond">
                                            </td>
                                            <td>250</td>
                                            <td>9&nbsp;000</td>
                                            <td>13&nbsp;000</td>
                                        </tr>
                                        <tr>
                                            <td>Brilliant III <small>Rad, GIA - WS1, Cr-6</small></td>
                                            <td>
                                                <img src="/theme/app/img/dia3.png" alt="diamond">
                                            </td>
                                            <td>280</td>
                                            <td>11&nbsp;000</td>
                                            <td>14&nbsp;500</td>
                                        </tr>
                                        <tr>
                                            <td>Brilliant&nbsp;Elizaveta <small>Rad, GIA - WS1, Cr-7</small></td>
                                            <td>
                                                <img src="/theme/app/img/dia1.png" alt="diamond">
                                            </td>
                                            <td>300</td>
                                            <td>10&nbsp;000</td>
                                            <td>12&nbsp;000</td>
                                        </tr>
                                        <tr>
                                            <td>Brilliant Elis <small>Ass, GIA - WS1, Cr-6</small></td>
                                            <td>
                                                <img src="/theme/app/img/dia2.png" alt="diamond">
                                            </td>
                                            <td>250</td>
                                            <td>9&nbsp;000</td>
                                            <td>13&nbsp;000</td>
                                        </tr>
                                        <tr>
                                            <td>Brilliant III <small>Rad, GIA - WS1, Cr-6</small></td>
                                            <td>
                                                <img src="/theme/app/img/dia3.png" alt="diamond">
                                            </td>
                                            <td>280</td>
                                            <td>11&nbsp;000</td>
                                            <td>14&nbsp;500</td>
                                        </tr>
                                        <tr>
                                            <td>Brilliant&nbsp;Elizaveta <small>Rad, GIA - WS1, Cr-7</small></td>
                                            <td>
                                                <img src="img/dia1.png" alt="diamond">
                                            </td>
                                            <td>300</td>
                                            <td>10&nbsp;000</td>
                                            <td>12&nbsp;000</td>
                                        </tr>
                                        <tr>
                                            <td>Brilliant Elis <small>Ass, GIA - WS1, Cr-6</small></td>
                                            <td>
                                                <img src="img/dia2.png" alt="diamond">
                                            </td>
                                            <td>250</td>
                                            <td>9&nbsp;000</td>
                                            <td>13&nbsp;000</td>
                                        </tr>
                                        <tr>
                                            <td>Brilliant III <small>Rad, GIA - WS1, Cr-6</small></td>
                                            <td>
                                                <img src="img/dia3.png" alt="diamond">
                                            </td>
                                            <td>280</td>
                                            <td>11&nbsp;000</td>
                                            <td>14&nbsp;500</td>
                                        </tr>
                                        <tr>
                                            <td>Brilliant&nbsp;Elizaveta <small>Rad, GIA - WS1, Cr-7</small></td>
                                            <td>
                                                <img src="img/dia1.png" alt="diamond">
                                            </td>
                                            <td>300</td>
                                            <td>10&nbsp;000</td>
                                            <td>12&nbsp;000</td>
                                        </tr>
                                        <tr>
                                            <td>Brilliant Elis <small>Ass, GIA - WS1, Cr-6</small></td>
                                            <td>
                                                <img src="img/dia2.png" alt="diamond">
                                            </td>
                                            <td>250</td>
                                            <td>9&nbsp;000</td>
                                            <td>13&nbsp;000</td>
                                        </tr>
                                        <tr>
                                            <td>Brilliant III <small>Rad, GIA - WS1, Cr-6</small></td>
                                            <td>
                                                <img src="img/dia3.png" alt="diamond">
                                            </td>
                                            <td>280</td>
                                            <td>11&nbsp;000</td>
                                            <td>14&nbsp;500</td>
                                        </tr>-->
                                        </tbody>
                                    </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="sep-cab-page__mid">
                            <div class="sep-cab-page__form">
                                <ul class="sep-cab-tabs sep-cab-tabs--form">
                                    <li class="active">
                                        <a href="#tab05">Market</a>
                                    </li>
                                    <li>
                                        <a href="#tab06">Limit</a>
                                    </li>
                                </ul>
                                <div class="form-tabs">
                                    <form class="form-tabs__tab" id="tab05">
                                        <div class="form-tabs__boxes">
                                            <label class="typebox">
                                                <input type="radio" class="typebox__input" name="type" checked>
                                                <span class="typebox__capt">Buy</span>
                                            </label>
                                            <label class="typebox">
                                                <input type="radio" class="typebox__input" name="type">
                                                <span class="typebox__capt">Sell</span>
                                            </label>
                                        </div>
                                        <div class="form-tabs__row">
                                            <label for="input03">Amount</label>
                                            <input type="text" value="0.00" id="input03">
                                        </div>
                                        <div class="form-tabs__submit">
                                            <h3>Total (ETH)</h3>
                                            <button class="form-tabs__subm">Place by order</button>
                                        </div>
                                    </form>
                                    <form class="form-tabs__tab" id="tab06">
                                        <div class="form-tabs__boxes">
                                            <label class="typebox">
                                                <input type="radio" class="typebox__input" name="type" checked>
                                                <span class="typebox__capt">Buy</span>
                                            </label>
                                            <label class="typebox">
                                                <input type="radio" class="typebox__input" name="type">
                                                <span class="typebox__capt">Sell</span>
                                            </label>
                                        </div>
                                        <div class="form-tabs__row">
                                            <label for="input01">Amount</label>
                                            <input type="text" value="0.00" id="input01">
                                        </div>
                                        <div class="form-tabs__row">
                                            <label for="input02">Limit price</label>
                                            <input type="text" value="0.00" id="input02" class="pdr">
                                            <small>USD</small>
                                        </div>
                                        <div class="form-tabs__info">
                                            <div class="form-tabs__info-top">
                                                <h3>Limit price</h3>
                                                <a href="#">Помощь</a>
                                            </div>
                                            <p>Сообщение не выбрано. Эта сделка может выполняться только заказчиком</p>
                                        </div>
                                        <div class="form-tabs__submit">
                                            <h3>Total (ETH)</h3>
                                            <button class="form-tabs__subm">Place by order</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <div class="sep-cab-page__activity">
                                <div class="sep-cab-page__heading">
                                    <h2>Общая активность</h2>
                                    <p>Средние показатели роста акций за год</p>
                                </div>
                                <div class="activity-graph">
                                    <img src="/theme/app/img/graph02.png" alt="graph">
                                </div>
                            </div>
                        </div>
                        <div class="sep-cab-page__side sep-cab-page__right">
                            <div class="sep-cab-page__heading">
                                <h2>Технический анализ</h2>
                                <p>График изменения цены долей бриллианта</p>
                            </div>
                            <!--
                            <form class="filterlist">
                                <div class="selectbox">
                                    <input type="text" value="Свеча" class="selectbox__input">
                                    <div class="selectbox__heading">Свеча</div>
                                    <ul class="selectbox__list">
                                        <li>
                                            <a href="#">Свеча</a>
                                        </li>
                                        <li>
                                            <a href="#">Что-то еще</a>
                                        </li>
                                    </ul>
                                </div>
                                <div class="selectbox">
                                    <input type="text" value="10 минут" class="selectbox__input">
                                    <div class="selectbox__heading">10 минут</div>
                                    <ul class="selectbox__list">
                                        <li>
                                            <a href="#">10 минут</a>
                                        </li>
                                        <li>
                                            <a href="#">20 минут</a>
                                        </li>
                                    </ul>
                                </div>
                                <div class="selectbox">
                                    <input type="text" value="Свеча" class="selectbox__input">
                                    <div class="selectbox__heading">Гистограмма</div>
                                    <ul class="selectbox__list">
                                        <li>
                                            <a href="#">Гистограмма</a>
                                        </li>
                                        <li>
                                            <a href="#">Круговая</a>
                                        </li>
                                    </ul>
                                </div>
                                <span class="filterlist__date">26 ноября</span>
                            </form>-->
                            <div class="main-trade-graph">
                                <div ng-controller="ChartController as vm">
                                    <div class="row">
                                        <highchart id="chart1" config="chartConfig" class="span10"></highchart>
                                    </div>
                                </div>
                                <!--
                                <img src="/theme/app/img/graph04.png" alt="graph">
                                -->
                            </div>
                            <div class="sub-trade-graph">
                                <h3>Динамика акций бриллианта за последний год</h3>
                                <img src="/theme/app/img/graph03.png" alt="graph">
                            </div>
                        </div>
                    </div>
                    <div class="sep-cab-page__bottom">
                        <div class="sep-cab-page__side sep-cab-page__bottom-left">
                            <ul class="sep-cab-tabs sep-cab-tabs--trade">
                                <li class="active">
                                    <a href="#tab03">Trade history</a>
                                </li>
                                <li>
                                    <a href="#tab04">Order Book</a>
                                </li>
                            </ul>
                            <div class="trades-tabs">
                                <div class="trades-tab" id="tab03">
                                    <div class="table-container">

                                        <div ng-controller="TradeOrderController as vm">
                                        <table class="diamont-table">
                                            <thead>
                                            <tr>
                                                <th>Сумма</th>
                                                <th>Цена</th>
                                                <th>Время</th>
                                            </tr>
                                            </thead>
                                            <tbody>

                                            <tr ng-repeat="tradeOrder in vm.historyTradeOrders">
                                                <td>{{tradeOrder.amount}}</td>
                                                <td class="up">
                                                    {{tradeOrder.price}}
                                                    <img src="/theme/app/img/up.png" alt="up">
                                                </td>
                                                <td>{{tradeOrder.executionDate | date:'yyyy-MM-dd HH:mm:ss' }}</td>
                                            </tr>

                                            <tr>
                                                <td>0.004523</td>
                                                <td class="up">
                                                    457.91
                                                    <img src="/theme/app/img/up.png" alt="up">
                                                </td>
                                                <td>11.36.20</td>
                                            </tr>
                                            <tr>
                                                <td>0.004523</td>
                                                <td class="up">
                                                    457.91
                                                    <img src="/theme/app/img/up.png" alt="up">
                                                </td>
                                                <td>11.36.20</td>
                                            </tr>
                                            <tr>
                                                <td>0.004523</td>
                                                <td class="up">
                                                    457.91
                                                    <img src="/theme/app/img/up.png" alt="up">
                                                </td>
                                                <td>11.36.20</td>
                                            </tr>
                                            <tr>
                                                <td>0.004523</td>
                                                <td class="up">
                                                    457.91
                                                    <img src="/theme/app/img/up.png" alt="up">
                                                </td>
                                                <td>11.36.20</td>
                                            </tr>
                                            <!--
                                            <tr>
                                                <td>0.004523</td>
                                                <td class="up">
                                                    457.91
                                                    <img src="img/up.png" alt="up">
                                                </td>
                                                <td>11.36.20</td>
                                            </tr>
                                            <tr>
                                                <td>0.004523</td>
                                                <td class="up">
                                                    457.91
                                                    <img src="img/up.png" alt="up">
                                                </td>
                                                <td>11.36.20</td>
                                            </tr>
                                            <tr>
                                                <td>0.004523</td>
                                                <td class="up">
                                                    457.91
                                                    <img src="img/up.png" alt="up">
                                                </td>
                                                <td>11.36.20</td>
                                            </tr>
                                            <tr>
                                                <td>0.004523</td>
                                                <td class="up">
                                                    457.91
                                                    <img src="img/up.png" alt="up">
                                                </td>
                                                <td>11.36.20</td>
                                            </tr>
                                            <tr>
                                                <td>0.004523</td>
                                                <td class="up">
                                                    457.91
                                                    <img src="img/up.png" alt="up">
                                                </td>
                                                <td>11.36.20</td>
                                            </tr>
                                            <tr>
                                                <td>0.004523</td>
                                                <td class="down">
                                                    457.91
                                                    <img src="img/down.png" alt="up">
                                                </td>
                                                <td>11.36.20</td>
                                            </tr>
                                            <tr>
                                                <td>0.004523</td>
                                                <td class="down">
                                                    457.91
                                                    <img src="img/down.png" alt="up">
                                                </td>
                                                <td>11.36.20</td>
                                            </tr>
                                            -->
                                            <tr>
                                                <td>0.004523</td>
                                                <td class="down">
                                                    457.91
                                                    <img src="/theme/app/img/down.png" alt="up">
                                                </td>
                                                <td>11.36.20</td>
                                            </tr>
                                            <tr>
                                                <td>0.004523</td>
                                                <td class="down">
                                                    457.91
                                                    <img src="/theme/app/img/down.png" alt="up">
                                                </td>
                                                <td>11.36.20</td>
                                            </tr>
                                            <tr>
                                                <td>0.004523</td>
                                                <td class="down">
                                                    457.91
                                                    <img src="/theme/app/img/down.png" alt="up">
                                                </td>
                                                <td>11.36.20</td>
                                            </tr>
                                            <tr>
                                                <td>0.004523</td>
                                                <td class="down">
                                                    457.91
                                                    <img src="/theme/app/img/down.png" alt="up">
                                                </td>
                                                <td>11.36.20</td>
                                            </tr>
                                            <tr>
                                                <td>0.004523</td>
                                                <td class="down">
                                                    457.91
                                                    <img src="/theme/app/img/down.png" alt="up">
                                                </td>
                                                <td>11.36.20</td>
                                            </tr>
                                            <tr>
                                                <td>0.004523</td>
                                                <td class="down">
                                                    457.91
                                                    <img src="/theme/app/img/down.png" alt="up">
                                                </td>
                                                <td>11.36.20</td>
                                            </tr>
                                            <tr>
                                                <td>0.004523</td>
                                                <td class="down">
                                                    457.91
                                                    <img src="/theme/app/img/down.png" alt="up">
                                                </td>
                                                <td>11.36.20</td>
                                            </tr>
                                            <tr>
                                                <td>0.004523</td>
                                                <td class="down">
                                                    457.91
                                                    <img src="/theme/app/img/down.png" alt="up">
                                                </td>
                                                <td>11.36.20</td>
                                            </tr>
                                            <tr>
                                                <td>0.004523</td>
                                                <td class="down">
                                                    457.91
                                                    <img src="/theme/app/img/down.png" alt="up">
                                                </td>
                                                <td>11.36.20</td>
                                            </tr>
                                            <tr>
                                                <td>0.004523</td>
                                                <td class="down">
                                                    457.91
                                                    <img src="/theme/app/img/down.png" alt="up">
                                                </td>
                                                <td>11.36.20</td>
                                            </tr>
                                            <tr>
                                                <td>0.004523</td>
                                                <td class="down">
                                                    457.91
                                                    <img src="/theme/app/img/down.png" alt="up">
                                                </td>
                                                <td>11.36.20</td>
                                            </tr>
                                            <tr>
                                                <td>0.004523</td>
                                                <td class="down">
                                                    457.91
                                                    <img src="/theme/app/img/down.png" alt="up">
                                                </td>
                                                <td>11.36.20</td>
                                            </tr>
                                            </tbody>
                                        </table>
                                        </div>
                                    </div>
                                </div>


                                <div ng-controller="BookOrderController as vm">
                                <div class="trades-tab" id="tab04">
                                    <div class="table-container">
                                        <table class="diamont-table diamont-table--pdb">
                                            <thead>
                                            <tr>
                                                <th>Продажи</th>
                                                <th>Цена</th>
                                                <th>Покупки</th>
                                            </tr>
                                            </thead>
                                            <tbody>

                                            <tr ng-repeat="tradeOrder in vm.bookOrder.buyOrders">
                                                <td>{{tradeOrder.amount}}</td>
                                                <td class="down">{{tradeOrder.price}}</td>
                                                <td>{{tradeOrder.creationDate | date:'dd/MM HH:mm:ss' }}</td>
                                            </tr>

                                            <!--
                                            <tr>
                                                <td class="down">4000</td>
                                                <td>49 808</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td class="down">500</td>
                                                <td>49 808</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td class="down">1000</td>
                                                <td>49 808</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td class="down">350</td>
                                                <td>49 808</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td class="down">222</td>
                                                <td>49 808</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td class="down">553</td>
                                                <td>49 808</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td class="down">50</td>
                                                <td>49 808</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td class="down">100</td>
                                                <td>49 808</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td class="down">100</td>
                                                <td>49 808</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td class="down">100</td>
                                                <td>49 808</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td class="down">50</td>
                                                <td>49 808</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td class="down">299</td>
                                                <td>49 808</td>
                                                <td></td>
                                            </tr>
                                            -->
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="table-container table-container--btp">
                                        <table class="diamont-table">
                                            <thead>
                                            <tr>
                                                <th>Spread</th>
                                                <th></th>
                                                <th></th>
                                                <th>0.01</th>
                                            </tr>
                                            </thead>
                                            <tbody>

                                            <tr ng-repeat="tradeOrder in vm.bookOrder.sellOrders">
                                                <td></td>
                                                <td  class="blue">{{tradeOrder.creationDate  | date:'dd/MM HH:mm:ss' }}</td>
                                                <td  class="blue">{{tradeOrder.amount}}</td>
                                                <td>{{tradeOrder.price}}</td>
                                            </tr>

                                            <!--
                                            <tr>
                                                <td></td>
                                                <td class="blue">49 808</td>
                                                <td class="blue">740</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td class="blue">49 804</td>
                                                <td class="blue">660</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td class="blue">49 790</td>
                                                <td class="blue">50</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td class="blue">49 787</td>
                                                <td class="blue">60</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td class="blue">49 786</td>
                                                <td class="blue">100</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td class="blue">49 752</td>
                                                <td class="blue">50</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td class="blue">49 752</td>
                                                <td class="blue">50</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td class="blue">49 752</td>
                                                <td class="blue">50</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td class="blue">49 752</td>
                                                <td class="blue">50</td>
                                                <td></td>
                                            </tr>
                                            <tr>
                                                <td></td>
                                                <td class="blue">49 752</td>
                                                <td class="blue">50</td>
                                                <td></td>
                                            </tr>-->
                                            </tbody>
                                        </table>
                                    </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="sep-cab-page__side sep-cab-page__bottom-right">
                            <ul class="sep-cab-tabs sep-cab-tabs--tables">
                                <li class="active">
                                    <a href="#tab01">Open Order</a>
                                </li>
                                <li>
                                    <a href="#tab02">Мои акции</a>
                                </li>
                            </ul>
                            <div class="sep-cab-tabs-container">
                                <div class="sep-cab-tab" id="tab01">
                                    <div class="table-container">

                                        <div ng-controller="TradeOrderController as vm">
                                            <table class="diamont-table">
                                            <thead>
                                            <tr>
                                                <th>Наименование</th>
                                                <th>Цена</th>
                                                <th>t покупки</th>
                                                <th>Статус</th>
                                                <th>t завершения</th>
                                                <th>Действие</th>
                                                <th>N акций</th>
                                            </tr>
                                            </thead>
                                            <tbody>

                                            <tr ng-repeat="tradeOrder in vm.liveTradeOrders">
                                                <!--
                                                <td>{{tradeOrder.id}}</td>
                                                -->
                                                <td>{{tradeOrder.diamond.name}}</td>
                                                <td>{{tradeOrder.price}}</td>
                                                <td>{{tradeOrder.creationDate | date:'yyyy-MM-dd HH:mm:ss' }}</td>
                                                <td>{{tradeOrder.traderOrderStatus}}</td>
                                                <td>{{tradeOrder.tradeOrderType}}</td>
                                                <!--
                                                <td>{{tradeOrder.amount}}</td>
                                                -->
                                                <td>{{tradeOrder.initialAmount}}</td>
                                                <td><a href="#" class="button black" ng-click="$event.preventDefault(); cancelTradeOrder(tradeOrder)">CANCEL</a></td>
                                            </tr>

                                            <!--
                                            <tr>
                                                <td>Brilliant Elizaveta</td>
                                                <td>10 000</td>
                                                <td>11:36:20</td>
                                                <td>Открыт</td>
                                                <td>12:36:30</td>
                                                <td>
                                                    <a href="#">Отменить</a>
                                                </td>
                                                <td>100</td>
                                            </tr>
                                            <tr>
                                                <td>Brilliant Elis</td>
                                                <td>12 000</td>
                                                <td>10:42:14</td>
                                                <td>Закрыт</td>
                                                <td>11:42:14</td>
                                                <td>
                                                    <a href="#">Отменить</a>
                                                </td>
                                                <td>200</td>
                                            </tr>
                                            <tr>
                                                <td>Brilliant Elizaveta</td>
                                                <td>10 000</td>
                                                <td>11:36:20</td>
                                                <td>Открыт</td>
                                                <td>12:36:30</td>
                                                <td>
                                                    <a href="#">Отменить</a>
                                                </td>
                                                <td>100</td>
                                            </tr>
                                            <tr>
                                                <td>Brilliant Elis</td>
                                                <td>12 000</td>
                                                <td>10:42:14</td>
                                                <td>Закрыт</td>
                                                <td>11:42:14</td>
                                                <td>
                                                    <a href="#">Отменить</a>
                                                </td>
                                                <td>200</td>
                                            </tr>
                                            <tr>
                                                <td>Brilliant Elizaveta</td>
                                                <td>10 000</td>
                                                <td>11:36:20</td>
                                                <td>Открыт</td>
                                                <td>12:36:30</td>
                                                <td>
                                                    <a href="#">Отменить</a>
                                                </td>
                                                <td>100</td>
                                            </tr>
                                            <tr>
                                                <td>Brilliant Elis</td>
                                                <td>12 000</td>
                                                <td>10:42:14</td>
                                                <td>Закрыт</td>
                                                <td>11:42:14</td>
                                                <td>
                                                    <a href="#">Отменить</a>
                                                </td>
                                                <td>200</td>
                                            </tr>
                                            <tr>
                                                <td>Brilliant Elizaveta</td>
                                                <td>10 000</td>
                                                <td>11:36:20</td>
                                                <td>Открыт</td>
                                                <td>12:36:30</td>
                                                <td>
                                                    <a href="#">Отменить</a>
                                                </td>
                                                <td>100</td>
                                            </tr>
                                            <tr>
                                                <td>Brilliant Elis</td>
                                                <td>12 000</td>
                                                <td>10:42:14</td>
                                                <td>Закрыт</td>
                                                <td>11:42:14</td>
                                                <td>
                                                    <a href="#">Отменить</a>
                                                </td>
                                                <td>200</td>
                                            </tr>
                                            <tr>
                                                <td>Brilliant Elizaveta</td>
                                                <td>10 000</td>
                                                <td>11:36:20</td>
                                                <td>Открыт</td>
                                                <td>12:36:30</td>
                                                <td>
                                                    <a href="#">Отменить</a>
                                                </td>
                                                <td>100</td>
                                            </tr>
                                            <tr>
                                                <td>Brilliant Elis</td>
                                                <td>12 000</td>
                                                <td>10:42:14</td>
                                                <td>Закрыт</td>
                                                <td>11:42:14</td>
                                                <td>
                                                    <a href="#">Отменить</a>
                                                </td>
                                                <td>200</td>
                                            </tr>
                                            -->
                                            </tbody>
                                        </table>
                                        </div>
                                    </div>
                                    <a href="#" class="sep-cab-tab__more">Показать предыдущие позиции</a>
                                </div>
                            </div>
                            <div class="sep-cab-tab" id="tab02">
                                <div class="table-container">

                                    <div ng-controller="StockController as vm">
                                        <table class="diamont-table">
                                        <thead>
                                        <tr>
                                            <th>Наименование</th>
                                            <!--
                                            <th>Цена</th>
                                            <th>t покупки</th>
                                            <th>Статус</th>
                                            <th>Цена продажи</th>
                                            -->
                                            <th>Количество</th>
                                            <th>Действие</th>
                                        </tr>
                                        </thead>
                                        <tbody>

                                        <tr ng-repeat="stock in vm.stocks">
                                            <!--
                                            <td>{{stock.id}}</td>
                                            <td>{{stock.diamond.id}}</td>
                                            -->
                                            <td>{{stock.diamond.name}}</td>
                                            <td>{{stock.amount}}</td>
                                            <td>
                                                <a href="#">Начать</a>
                                            </td>
                                        </tr>
                                        <!--
                                        <tr>
                                            <td>Brilliant Elizaveta</td>
                                            <td>10 000</td>
                                            <td>11:36:20</td>
                                            <td>Резерв</td>
                                            <td>-</td>
                                            <td>-</td>

                                        </tr>
                                        <tr>
                                            <td>Brilliant Elis</td>
                                            <td>12 000</td>
                                            <td>10:42:14</td>
                                            <td>На аукционе</td>
                                            <td>15000</td>
                                            <td>17900</td>
                                            <td>
                                                <a href="#">Продать</a>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Brilliant QITI</td>
                                            <td>14 000</td>
                                            <td>11:36:20</td>
                                            <td>Резерв</td>
                                            <td>-</td>
                                            <td>-</td>
                                            <td>
                                                <a href="#">Начать</a>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Brilliant Elizaveta</td>
                                            <td>10 000</td>
                                            <td>11:36:20</td>
                                            <td>Резерв</td>
                                            <td>-</td>
                                            <td>-</td>
                                            <td>
                                                <a href="#">Начать</a>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Brilliant Elis</td>
                                            <td>12 000</td>
                                            <td>10:42:14</td>
                                            <td>На аукционе</td>
                                            <td>15000</td>
                                            <td>17900</td>
                                            <td>
                                                <a href="#">Продать</a>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Brilliant QITI</td>
                                            <td>14 000</td>
                                            <td>11:36:20</td>
                                            <td>Резерв</td>
                                            <td>-</td>
                                            <td>-</td>
                                            <td>
                                                <a href="#">Начать</a>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Brilliant Elizaveta</td>
                                            <td>10 000</td>
                                            <td>11:36:20</td>
                                            <td>Резерв</td>
                                            <td>-</td>
                                            <td>-</td>
                                            <td>
                                                <a href="#">Начать</a>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Brilliant Elis</td>
                                            <td>12 000</td>
                                            <td>10:42:14</td>
                                            <td>На аукционе</td>
                                            <td>15000</td>
                                            <td>17900</td>
                                            <td>
                                                <a href="#">Продать</a>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Brilliant QITI</td>
                                            <td>14 000</td>
                                            <td>11:36:20</td>
                                            <td>Резерв</td>
                                            <td>-</td>
                                            <td>-</td>
                                            <td>
                                                <a href="#">Начать</a>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>Brilliant Elizaveta</td>
                                            <td>10 000</td>
                                            <td>11:36:20</td>
                                            <td>Резерв</td>
                                            <td>-</td>
                                            <td>-</td>
                                            <td>
                                                <a href="#">Начать</a>
                                            </td>
                                        </tr>-->
                                        </tbody>
                                    </table>
                                    </div>
                                </div>
                                <a href="#" class="sep-cab-tab__more">Показать предыдущие позиции</a>
                            </div>
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
