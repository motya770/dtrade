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
                    <h2 class="def-cab-page__heading">My history</h2>
                    <ul class="lk-tabs-heading">
                        <li class="active">
                            <a href="#tab01">Balance activity</a>
                        </li>
                        <li>
                            <a href="#tab02">Trade history</a>
                        </li>
                        <li>
                            <a href="#tab03">Deposits and withdraws</a>
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
                                            <th>Date</th>
                                            <th>Operation type</th>
                                            <th>Sum ($)</th>
                                            <th>Balance</th>
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
                                    <a href="#" ng-click="getPreviousBalanceActivities(vm.balanceActivities.number)" class="sep-cab-tab__more">Previous activities</a>
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
                                            <th>Date</th>
                                            <th>Name</th>
                                            <th>№ Amount</th>
                                            <th>Price</th>
                                            <th>Sum</th>
                                            <th>Status</th>
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
                                            <td>{{tradeOrder.initialAmount}}</td>
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
                                    <a href="#" ng-click="getPreviousAccountHistoryOrders(vm.accountHistoryTradeOrders.number)" class="sep-cab-tab__more">Previous orders</a>
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
                        <div class="lk-main-tab" id="tab03">
                            <div class="lk-main-tab__table" style="width: 692px; min-height: 400px;">

                                <!--
                                <a href="//www.free-kassa.ru/"><img src="//www.free-kassa.ru/img/fk_btn/7.png"></a>
                                <a href="#" onclick="load_form()">Оплатить</a>
                                <script src="//www.free-kassa.ru/widget/w.js"></script>
                                <script type="text/javascript">
                                    function load_form() {
                                        var form = new FK();
                                        form.loadWidget({
                                            merchant_id: '75199',
                                            amount: '10',
                                            order_id: 'ORDER_ID',
                                            email: 'EMAIL',
                                            phone: 'PHONE',
                                            sign: 'SIGN',
                                            us_user: 1,
                                            us_desc: 'Test',
                                        });
                                    }
                                </script>
                                <iframe src="http://www.free-kassa.ru/merchant/forms.php?gen_form=1&m=75199&default-sum=50&button-text=Оплатить&encoding=CP1251&type=v3&id=114257&lang=en"  width="590" height="320" frameBorder="0" target="_parent" ></iframe>
                                -->

                                <form action="https://www.coinpayments.net/index.php" method="post" style="margin-left: 20px; margin-top: 20px;">
                                    <input type="hidden" name="cmd" value="_pay">
                                    <input type="hidden" name="reset" value="1">
                                    <input type="hidden" name="merchant" value="1fb3cd572acffff43b1c0356d5429f1c">
                                    <input type="hidden" name="item_name" value="Diaminds Deposit">
                                    <input type="hidden" name="currency" value="USD">
                                    <input type="hidden" name="amountf" value="5.00000000">
                                    <input type="hidden" name="quantity" value="1">
                                    <input type="hidden" name="allow_quantity" value="0">
                                    <input type="hidden" name="want_shipping" value="0">
                                    <input type="hidden" name="success_url" value="www.diaminds.io">
                                    <input type="hidden" name="allow_extra" value="0">
                                    <input type="image" src="https://www.coinpayments.net/images/pub/buynow.png" alt="Buy Now with CoinPayments.net">
                                </form>
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
