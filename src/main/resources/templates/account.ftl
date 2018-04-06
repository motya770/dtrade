<!DOCTYPE html>
<html ng-app="diamondApp">

<head>

    <#include "parts/head.ftl">
    <script src="/content/js/app/balanceactivity.service.js"></script>
    <script src="/content/js/app/balanceactivity.controller.js"></script>

</head>

<body class="page">

<!-- Custom HTML -->
<div class="site">
    <#include "parts/caption.ftl">
    <#include "parts/menu.ftl">

    <main class="content">
        <#include "parts/portfolio.ftl">
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
                            <div class="lk-main-tab__table" style="width: 692px;">
                                <div class="table-container" ng-controller="BalanceActivityController as vm">
                                    <div>
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

                                        <tr ng-repeat="balanceActivity in vm.balanceActivities.content">
                                            <td>{{balanceActivity.id}}</td>
                                            <td>{{balanceActivity.createDate | date:'yyyy-MM-dd HH:mm:ss'}}</td>
                                            <td>{{balanceActivity.balanceActivityType}}</td>
                                            <td>{{balanceActivity.amount}}</td>
                                            <td>{{balanceActivity.balanceSnapshot}}</td>
                                        </tr>

                                        </tbody>
                                    </table>
                                    </div>
                                    <a href="#" ng-click="getPreviousBalanceActivities(vm.balanceActivities.number)" class="sep-cab-tab__more">Показать предыдущие изменения</a>
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
                            <div class="lk-main-tab__table" style="width: 692px;">
                                <div class="table-container" ng-controller="TradeOrderAccountController as vm">
                                    <div >
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
                                        <tr ng-repeat="tradeOrder in vm.accountHistoryTradeOrders.content">
                                            <td>{{tradeOrder.id}}</td>
                                            <td>{{tradeOrder.executionDate | date:'yyyy-MM-dd HH:mm:ss' }}</td>
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
                                    <a href="#" ng-click="getPreviousAccountHistoryOrders(vm.accountHistoryTradeOrders.number)" class="sep-cab-tab__more">Показать предыдущие позиции</a>
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
    <#include "parts/footer.ftl">
</div>


<#include "parts/bootom-scripts.ftl">


</body>
</html>
