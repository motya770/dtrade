<!DOCTYPE html>
<html ng-app="diamondApp">

<head>
    <#include "parts/head.ftl">
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
                                <div class="table-container" ng-controller="BalanceActivityController as vm" ng-cloak>
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
                                            <td>{{balanceActivity.balanceActivityType | lowercase}}</td>
                                            <td>{{balanceActivity.amount | number}}</td>
                                            <td>{{balanceActivity.balanceSnapshot | number}}</td>
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
                            <div class="lk-main-tab__table" style="width: 900px;">
                                <div class="table-container" ng-controller="TradeOrderAccountController as vm" ng-cloak>
                                    <div >
                                        <table class="diamont-table">
                                        <thead>
                                        <tr>
                                            <th>№</th>
                                            <th>Date created</th>
                                            <th>Date executed</th>
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
                                            <td>{{tradeOrder.creationDate | date:'yyyy-MM-dd HH:mm:ss' }}</td>
                                            <td>{{tradeOrder.executionDate | date:'yyyy-MM-dd HH:mm:ss' }}</td>
                                            <td>{{tradeOrder.diamond.name}}</td>
                                            <td>{{tradeOrder.initialAmount | number : 2}}</td>
                                            <td>{{tradeOrder.price | number : 2}}</td>
                                            <td>{{tradeOrder.price * tradeOrder.initialAmount | number : 2}}</td>
                                            <td>{{tradeOrder.traderOrderStatus | lowercase}}</td>
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
                        <div class="lk-main-tab" id="tab03" ng-controller="CoinPaymentController as cp" ng-cloak>
                            <div class="lk-main-tab__table" style="width: 1175px; min-height: 400px;">



                                <div ng-controller="AccountController as vm" class="lk-main-tab__info" style="width:800px; margin-left: 20px;" ng-cloak>

                                    <md-content class="md-no-momentum" style="min-width: 300px; margin-bottom: 20px; margin-top: 20px;">
                                        <h2 class="account-withdraw">Deposit (PayPal and Crypto)</h2>
                                        <!--
                                        <input ng-model="cp.depositRequest.amount" type="number" step="0.01" style="margin-top: 10px; margin-left: 20px;">
            -->
                                        <md-content>
                                            <md-input-container class="md-icon-float md-icon-right md-block">
                                                <label>Deposit Amount ($)</label>
                                                <md-icon md-svg-src="img/icons/ic_card_giftcard_24px.svg"></md-icon>
                                                <input ng-model="cp.depositRequest.amount" type="number" step="0.01">
                                                <md-icon md-svg-src="img/icons/ic_euro_24px.svg"></md-icon>
                                            </md-input-container>
                                        </md-content>


                                    <form action="https://www.coinpayments.net/index.php" method="post" style="margin-left: 20px; margin-top: 20px; margin-bottom: 20px;">
                                            <input type="hidden" name="cmd" value="_pay">
                                            <input type="hidden" name="reset" value="1">
                                            <input type="hidden" name="merchant" value="1fb3cd572acffff43b1c0356d5429f1c">
                                            <input type="hidden" name="item_name" value="Diaminds Deposit">
                                            <input type="hidden" name="currency" value="USD">
                                            <input type="hidden" name="first_name" value="Trading platform">
                                            <input type="hidden" name="last_name" value="user">
                                            <input type="hidden" name="amountf" value="{{cp.depositRequest.amount}}">
                                            <input type="hidden" name="quantity" value="1">
                                            <input type="hidden" name="allow_quantity" value="0">
                                            <input type="hidden" name="want_shipping" value="0">
                                            <input type="hidden" name="success_url" value="www.diaminds.io/trade">
                                            <input type="hidden" name="allow_extra" value="0">
                                            <input type="hidden" name="email" value="{{vm.account.mail}}">
                                            <md-button style="margin-top: -40px;" class="md-raised md-primary" type="submit" >Deposit</md-button>
                                            <!--
                                            <input type="image" src="https://www.coinpayments.net/images/pub/buynow.png" alt="Buy Now with CoinPayments.net">
                                            -->
                                        </form>
                                    </md-content>
                                </div>

                                <div style="margin: 20px; width: 600px">
                                    <md-content class="md-no-momentum" style="min-width: 500px;">
                                        <h2 class="account-withdraw">Withdraw</h2>
                                        <md-input-container class="md-icon-float md-block">
                                            <label>Currency</label>
                                            <md-icon md-svg-src="/theme/app/img/icons/ic_person_24px.svg" class="currency"></md-icon>
                                            <input ng-model="cp.withdrawRequest.currencyFiat" type="text" disabled>
                                        </md-input-container>

                                        <md-input-container class="md-icon-float md-icon-right md-block">
                                            <label>Withdraw Amount ($)</label>
                                            <md-icon md-svg-src="/theme/app/img/icons/ic_card_giftcard_24px.svg"></md-icon>
                                            <input ng-model="cp.withdrawRequest.amount" type="number" step="0.01">
                                            <md-icon md-svg-src="/theme/app/img/icons/ic_euro_24px.svg"></md-icon>
                                        </md-input-container>

                                        <md-input-container style="margin-left: 34px; margin-bottom: 28px; width: 100px;">
                                            <label>Crypto Coin</label>
                                            <md-select ng-model="cp.withdrawRequest.currencyCoin">
                                                <md-option><em>None</em></md-option>
                                                <md-option ng-repeat="state in ['BTC', 'ETH', 'USDT']" ng-value="state" ng-disabled="$index === 1">
                                                    {{state}}
                                                </md-option>
                                            </md-select>
                                        </md-input-container>

                                        <md-input-container class="md-icon-float md-block">
                                            <label>Your crypto currency address</label>
                                            <md-icon md-svg-src="/theme/app/img/icons/ic_person_24px.svg" class="address"></md-icon>
                                            <input ng-model="cp.withdrawRequest.address" type="text">
                                        </md-input-container>

                                        <md-button class="md-raised md-primary" ng-click="createWithdraw(cp.withdrawRequest);">Withdraw</md-button>
                                    </md-content>
                                </div>


                                <div class="table-container" >
                                    <div>
                                        <table class="diamont-table">
                                            <thead>
                                            <tr>
                                                <th>№</th>
                                                <th>Date</th>
                                                <th>Status</th>
                                                <th>Type</th>
                                                <th>Fiat currency</th>
                                                <th>Sum in fiat</th>
                                                <th>Crypto coin</th>
                                                <th>Sum in coin</th>
                                                <th>Transaction Id</th>
                                                <th>Ipn ID</th>
                                                <th>External ID</th>
                                            </tr>
                                            </thead>
                                            <tbody>

                                            <tr ng-repeat="coinPayment in cp.coinPayments.content track by coinPayment.id">

                                                <td>{{coinPayment.id}}</td>
                                                <td>{{coinPayment.creationDate | date:'yyyy-MM-dd HH:mm:ss' }}</td>
                                                <td>{{coinPayment.coinPaymentStatus | lowercase}}</td>
                                                <td>{{coinPayment.coinPaymentType | lowercase}}</td>

                                                <td ng-if="coinPayment.depositRequest">{{coinPayment.depositRequest.currencyUsd}}</td>
                                                <td ng-if="coinPayment.depositRequest">{{coinPayment.depositRequest.amountUsd | number}}</td>
                                                <td ng-if="coinPayment.depositRequest">{{coinPayment.depositRequest.currencyCoin}}</td>
                                                <td ng-if="coinPayment.depositRequest">{{coinPayment.depositRequest.amountCoin | number : 6}}</td>
                                                <td ng-if="coinPayment.depositRequest">{{coinPayment.depositRequest.transactionId}}</td>
                                                <td ng-if="coinPayment.depositRequest">{{coinPayment.depositRequest.ipnId}}</td>
                                                <td ng-if="coinPayment.depositRequest">-</td>

                                                <td ng-if="coinPayment.inWithdrawRequest">{{coinPayment.inWithdrawRequest.currencyUsd}}</td>
                                                <td ng-if="coinPayment.inWithdrawRequest">{{coinPayment.inWithdrawRequest.amountUsd | number}}</td>
                                                <td ng-if="coinPayment.inWithdrawRequest">{{coinPayment.inWithdrawRequest.currencyCoin}}</td>
                                                <td ng-if="coinPayment.inWithdrawRequest">{{coinPayment.inWithdrawRequest.amountCoin}}</td>
                                                <td ng-if="coinPayment.inWithdrawRequest">{{coinPayment.inWithdrawRequest.transactionId}}</td>
                                                <td ng-if="coinPayment.inWithdrawRequest">{{coinPayment.inWithdrawRequest.ipnId}}</td>
                                                <td ng-if="coinPayment.inWithdrawRequest">{{coinPayment.inWithdrawRequest.id}}</td>

                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <div class="lk-main-tab__info">

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
<script src="/content/js/app/balanceactivity.service.js"></script>
<script src="/content/js/app/balanceactivity.controller.js"></script>
<script src="/content/js/app/coinpayment.controller.js"></script>

</body>
</html>


<!--
                                {{vm.withdrawRequest}}
                                <div><input type="text" ng-model="vm.withdrawRequest.currencyCoin"></div>
                                <div><input type="text" ng-model="vm.withdrawRequest.currencyFiat"></div>
                                <div><input type="text" ng-model="vm.withdrawRequest.address"></div>
                                <div><input type="text" ng-model="vm.withdrawRequest.amount"></div>
                                <div></div>
                                <input type="button" value="Create Withdraw" ng-click="createWithdraw(vm.withdrawRequest);">
                                -->