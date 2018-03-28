<!DOCTYPE html>
<html ng-app="diamondApp">

<head>

    <#include "head.ftl">
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



                    <div class="lk-main-tabs">
                        <div class="lk-main-tab" id="tab01">
                            <div class="lk-main-tab__table">
                                <div class="table-container">
                                    <div ng-controller="BalanceActivityController as vm">
                                        <table class="diamont-table">
                                        <thead>
                                        <tr>
                                            <th>№</th>
                                            <th>Дата</th>
                                            <th>Сумма, руб</th>
                                            <th>Вид операции</th>
                                            <th>Баланс на момент</th>
                                        </tr>
                                        </thead>
                                        <tbody>

                                        <tr ng-repeat="balanceActivity in vm.balanceActivities">
                                            <td>{{balanceActivity.id}}</td>
                                            <td>{{balanceActivity.createDate}}</td>
                                            <td>{{balanceActivity.balanceActivityType}}</td>
                                            <td>{{balanceActivity.amount}}</td>
                                            <td>{{balanceActivity.balanceSnapshot}}</td>
                                        </tr>

                                        </tbody>
                                    </table>
                                    </div>
                                </div>
                            </div>
                            <div class="lk-main-tab__info">
                                <!--
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
                                </div>-->
                            </div>
                        </div>
                        <div class="lk-main-tab" id="tab02">
                            <div class="lk-main-tab__table">
                                <div class="table-container">
                                    <div ng-controller="TradeOrderAccountController as vm">
                                        <table class="diamont-table">
                                        <thead>
                                        <tr>
                                            <th>№</th>
                                            <th>Дата</th>
                                            <th>Акция</th>
                                            <th>№ Акции</th>
                                            <th>Цена</th>
                                            <th>Сумма</th>
                                            <th>Статус</th>
                                            <!--
                                            <th>Баланс</th>
                                            -->
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr ng-repeat="tradeOrder in vm.accountHistoryTradeOrders">
                                            <td>{{tradeOrder.id}}</td>
                                            <td>{{tradeOrder.executionDate}}</td>
                                            <td>{{tradeOrder.diamond.name}}</td>
                                            <td>{{tradeOrder.amount}}</td>
                                            <td>{{tradeOrder.price}}</td>
                                            <td>{{tradeOrder.price * tradeOrder.amount}}</td>
                                            <td>{{tradeOrder.traderOrderStatus}}</td>
                                            <!--
                                            <td>{{tradeOrder.}} </td>
                                            -->
                                        </tr>
                                        </tbody>
                                    </table>
                                    </div>
                                </div>
                            </div>
                            <div class="lk-main-tab__info">
                                <!--
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
                                </div>-->
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
