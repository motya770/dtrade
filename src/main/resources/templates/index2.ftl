<!DOCTYPE html>
<html ng-app="diamondApp">

<head>
<#include "parts/head.ftl">

<script type="text/javascript">
 function primitiveRotator1(el) {
     el.src = "/image/diamond-image?image=" + 3;
 }
</script>

</head>

<body class="page">

<div class="site class=" ng-cloak ng-controller="TopController" class="md-padding dialogdemoBasicUsage" id="popupContainer">
   <div class="spinner" ng-hide="loaded">
       <div class="rect1"></div>
       <div class="rect2"></div>
       <div class="rect3"></div>
       <div class="rect4"></div>
       <div class="rect5"></div>
     </div>
   <div ng-cloak="" ng-show="loaded" ng-controller="ConfigController as cc">

    <#include "parts/caption.ftl">
    <#include "parts/menu.ftl">

    <main class="content" >

        <#include "parts/portfolio.ftl">

        <div class="sep-cab-page">
            <div class="sep-cab-page__inner">
                <div class="sep-cab-page__content">
                    <div class="sep-cab-page__top">
                        <div class="sep-cab-page__side sep-cab-page__left" ng-controller="AvailableController as vm" style="height:491px;">
                            <div class="sep-cab-page__heading">
                                <h2>{{cc.config.assetName}} listing</h2>
                                <p>All {{cc.config.assetName}} are available</p>
                            </div>

                            <form class="searchform">
                                <input type="text" class="searchform__inp" ng-model="searchInputValue" placeholder="Enter the name of the {{cc.config.assetName}}">
                                <button class="searchform__btn" ng-click="getAvailableByName();">Find</button>
                            </form>

                            <div class="type-diamond-table">
                                <div class="table-container">
                                    <div >
                                      <table class="diamont-table">
                                        <thead>
                                        <tr>
                                            <th>{{cc.config.assetNameForListing}}</th>
                                            <th></th>

                                            <th>Bid <!--<span class="inf">?</span>--></th>
                                            <th>Ask <!--<span class="inf">?</span>--></th>

                                            <th>N</th>
                                        </tr>
                                        </thead>
                                        <tbody>

                                            <tr ng-repeat="diamond in vm.availableDiamonds" ng-click="chooseAvailableDiamond(diamond)">
                                                <td>{{diamond.name}}
                                                    <small ng-if="cc.config.assetType=='DIAMOND'">
                                                    {{diamond.diamondType | diamondTypeFilter }},
                                                     GIA - {{diamond.clarity}},
                                                     Cr - {{diamond.carats}}
                                                    </small>
                                                </td>
                                                <td>
                                                    <div class="dialog-demo-content" layout="row" layout-wrap="" >
                                                         <input ng-if="cc.config.assetType=='DIAMOND'" type="image" src="/theme/app/img/dia1.png" alt="Submit Form" class="md-primary md-raised" ng-click="showAdvanced( $event)" />
                                                         <input ng-if="cc.config.assetType=='WINE'" style="height: 30px;" type="image" src="/theme/app/img/bottle.png" alt="Submit Form" class="md-primary md-raised" ng-click="showAdvanced( $event)" />
                                                    </div>
                                                </td>
                                                <td>{{diamond.askBidPair.first}} </td>
                                                <td>{{diamond.askBidPair.second}}</td>
                                                <td><span ng-if="!diamond.hideTotalStockAmount">{{diamond.totalStockAmount/10000000 | number}} mln </span></td>
                                            </tr>
                                                    <script type="text/ng-template" id="dialog1.tmpl.html">
                                                        <md-dialog aria-label="Diamond">
                                                            <form ng-cloak>
                                                                <md-toolbar>
                                                                    <div class="md-toolbar-tools">
                                                                        <h2>Diamond</h2>
                                                                        <span flex></span>
                                                                        <md-button class="md-icon-button" ng-click="cancel()">
                                                                            <img src="/theme/app/img/ic_close.png" aria-label="Close dialog">
                                                                        </md-button>
                                                                    </div>
                                                                </md-toolbar>
                                                                <md-dialog-content>
                                                                    <div class="md-dialog-content">
                                                                        <img id="pix" src="{{diamondImage}}" alt="" ng-click="primitiveRotator(this);"/>
                                                                    </div>
                                                                </md-dialog-content>
                                                            </form>
                                                        </md-dialog>
                                                    </script>

                                        <tr ng-if="cc.config.assetType=='DIAMOND'" style="width: 100%; background-color: #e7e7e7">
                                            <td>Elisabeth III <small>Rad, GIA - VSS2, Cr-6</small></td>
                                                    <td>
                                                       <div layout="row" layout-wrap="" >
                                                            <input type="image" src="/theme/app/img/dia2.png" />
                                                        </div>
                                                    </td>
                                            <td>0.4</td>
                                            <td>0.42&nbsp;</td>
                                            <td>7 mln</td>
                                        </tr>
                                            <tr ng-if="cc.config.assetType=='DIAMOND'" style="width: 100%; background-color: #e7e7e7">
                                                <td style="width: 100%;">White Sea <small>Ova, GIA - VS2, Cr-3</small></td>
                                                <td>
                                                    <div layout="row" layout-wrap="" >
                                                        <input type="image" src="/theme/app/img/dia1.png" />
                                                    </div>
                                                </td>
                                                <td>0.4</td>
                                                <td>0.43&nbsp;</td>
                                                <td>4 mln</td>
                                            </tr>
                                            <tr ng-if="cc.config.assetType=='DIAMOND'" style="width: 100%; background-color: #e7e7e7">
                                                <td style="width: 100%;">Ashberg <small>Pea, GIA - VS1, Cr-5</small></td>
                                                <td>
                                                    <div layout="row" layout-wrap="" >
                                                        <input type="image" src="/theme/app/img/dia3.png" />
                                                    </div>
                                                </td>
                                                <td>21.4</td>
                                                <td>22.5&nbsp;</td>
                                                <td>10 mln</td>
                                            </tr>

                                            <tr ng-if="cc.config.assetType=='WINE'" style="width: 100%; background-color: #e7e7e7">
                                                <td style="width: 100%;">Ashberg <small>Corton 1er cru</small></td>
                                                <td>
                                                    <div layout="row" layout-wrap="" >
                                                        <input type="image" style="height: 30px;" src="/theme/app/img/bottle.png" />
                                                    </div>
                                                </td>
                                                <td>1.1</td>
                                                <td>1.2&nbsp;</td>
                                                <td>80 k</td>
                                            </tr>


                                            <tr ng-if="cc.config.assetType=='WINE'" style="width: 100%; background-color: #e7e7e7">
                                                <td style="width: 100%;">Ashberg <small>Corton clos du chapitre</small></td>
                                                <td>
                                                    <div layout="row" layout-wrap="" >
                                                        <input type="image" style="height: 30px;" src="/theme/app/img/bottle.png" />
                                                    </div>
                                                </td>
                                                <td>2.4</td>
                                                <td>3.5&nbsp;</td>
                                                <td>50 k</td>
                                            </tr>


                                            <tr ng-if="cc.config.assetType=='WINE'" style="width: 100%; background-color: #e7e7e7">
                                                <td style="width: 100%;">Ashberg <small>Corton les valozieres</small></td>
                                                <td>
                                                    <div layout="row" layout-wrap="" >
                                                        <input type="image"  style="height: 30px;" src="/theme/app/img/bottle.png" />
                                                    </div>
                                                </td>
                                                <td>9.4</td>
                                                <td>10.5&nbsp;</td>
                                                <td>10 k</td>
                                            </tr>
                                            <!--
                                            <tr style="width: 100%; background-color: #e7e7e7">
                                                <td style="width: 100%;">Albert <small>Hea, GIA - VS1, Cr-8</small></td>
                                                <td>
                                                    <div layout="row" layout-wrap="" >
                                                        <input type="image" src="/theme/app/img/dia2.png" />
                                                    </div>
                                                </td>
                                                <td>8.1</td>
                                                <td>8.3&nbsp;</td>
                                                <td>21 mln</td>
                                            </tr>-->

                                        </tbody>
                                    </table>
                                        <div class="coming-soon">New offerings coming soon ... </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="sep-cab-page__mid">
                            <div class="sep-cab-page__form" style="height: 491px;">
                                <ul class="sep-cab-tabs sep-cab-tabs--form">
                                    <li class="active">
                                        <a href="#tab05">Market</a>
                                    </li>
                                    <li>
                                        <a href="#tab06">Limit</a>
                                    </li>
                                </ul>

                                <div ng-controller="BidderController as vm">
                                    <div class="form-tabs">
                                    <form class="form-tabs__tab" id="tab05">
                                        <div class="form-tabs__boxes">
                                            <div class="form-tabs__info-top diamond-bid-name">
                                                <div>{{vm.diamond.name}}</div>
                                                <p ng-if="cc.config.assetType=='DIAMOND'" >{{vm.diamond.diamondType | diamondTypeFilter}},
                                                    Cr - {{vm.diamond.carats}}, GIA - {{vm.diamond.clarity}}</p>
                                            </div>
                                        </div>
                                        <div class="form-tabs__boxes">
                                            <label class="typebox">
                                                <input type="radio" class="typebox__input" name="type" checked>
                                                <span class="typebox__capt" ng-click="buyOption(vm.tradeOrder);" >Buy</span>
                                            </label>
                                            <label class="typebox">
                                                <input type="radio" class="typebox__input" name="type">
                                                <span class="typebox__capt" ng-click="sellOption(vm.tradeOrder);">Sell</span>
                                            </label>
                                        </div>
                                        <div class="form-tabs__row">
                                            <label for="input03">Amount</label>
                                            <input type="text" ng-model="vm.tradeOrder.amount" value="0.00" id="input03">
                                        </div>
                                        <div class="form-tabs__submit">
                                            <h3>Total ({{vm.diamond.baseCurrency}})</h3>
                                            <button class="form-tabs__subm" ng-click="alert('test'); createTradeOrder(vm.tradeOrder, vm.diamond, 'MARKET');">Place order</button>
                                        </div>
                                    </form>
                                    <form class="form-tabs__tab" id="tab06" style="display: none;">
                                        <div class="form-tabs__boxes">
                                            <div class="form-tabs__info-top diamond-bid-name">
                                                <div>{{vm.diamond.name}}</div>
                                                <p ng-if="cc.config.assetType=='DIAMOND'">{{vm.diamond.diamondType | diamondTypeFilter}},
                                               Cr - {{vm.diamond.carats}}, GIA - {{vm.diamond.clarity}}</p>
                                            </div>
                                        </div>
                                        <div class="form-tabs__boxes">
                                            <label class="typebox">
                                                <input type="radio" class="typebox__input" name="type" checked>
                                                <span class="typebox__capt" ng-click="buyOption(vm.tradeOrder);">Buy</span>
                                            </label>
                                            <label class="typebox">
                                                <input type="radio" class="typebox__input" name="type">
                                                <span class="typebox__capt" ng-click="sellOption(vm.tradeOrder);">Sell</span>
                                            </label>
                                        </div>
                                        <div class="form-tabs__row">
                                            <label for="input01">Amount</label>
                                            <input type="number" ng-init="vm.tradeOrder.amount=0" ng-model="vm.tradeOrder.amount" value="0.00" id="input01" min="0">
                                        </div>
                                        <div class="form-tabs__row">
                                            <label for="input02">Limit price</label>
                                            <input type="number"  ng-init="vm.tradeOrder.price=0" ng-model="vm.tradeOrder.price"  value="0.00" id="input02" class="pdr" min="0" step="0.01">
                                            <small>{{vm.diamond.baseCurrency}}</small>
                                        </div>
                                        <!--
                                        <div class="form-tabs__info">
                                            <div class="form-tabs__info-top">
                                                <h3>Limit price</h3>
                                                <a href="#">Помощь</a>
                                            </div>
                                            <p>Сообщение не выбрано. Эта сделка может выполняться только заказчиком</p>
                                        </div>-->
                                        <div class="form-tabs__submit">
                                            <h3>Total ({{vm.diamond.baseCurrency}}): {{vm.tradeOrder.amount * vm.tradeOrder.price | number:2}}</h3>
                                            <button class="form-tabs__subm"  ng-click="createTradeOrder(vm.tradeOrder, vm.diamond, 'LIMIT')">Place order</button>
                                        </div>
                                    </form sty sty>
                                </div>
                                </div>
                            </div>
                            <!--
                            <div class="sep-cab-page__activity">
                                <div class="sep-cab-page__heading">
                                    <h2>General activity</h2>
                                    <p>Average market changes for year</p>
                                </div>
                                <div class="activity-graph">
                                    <img src="/theme/app/img/graph02.png" alt="graph">
                                </div>
                            </div>-->
                        </div>
                        <div class="sep-cab-page__side sep-cab-page__right" style="height: 491px;">
                            <div class="">
                                <ul class="sep-cab-tabs sep-cab-tabs--graph">
                                    <li class="active">
                                        <a href="#tab11">Graph</a>
                                    </li>
                                    <li>
                                        <a href="#tab12">Depth</a>
                                    </li>
                                </ul>
                                <!--
                                <h2>Technical analysis</h2>
                                <p>Graph of diamond share prices</p>-->
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
                            <div class="main-trade-graph" style="margin-top:0px; padding-bottom: 16px;">
                                <div class="form-tabs">
                                    <div ng-controller="ChartController as vm">
                                        <div  class="graph__tab" id="tab11" class="row" style="position: relative; width: 100%;">
                                            <div id="container" style="position: relative; width: 100%;"></div>
                                        </div>

                                        <div  class="graph__tab" id="tab12" class="row" style="display: none; position: relative; width: 100%;">
                                            <div id="containerDepth" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
                                        </div>
                                    </div>
                                </div>
                                <!--
                                <img src="/theme/app/img/graph04.png" alt="graph">
                                -->
                            </div>

                            <!--
                            <div class="sub-trade-graph">
                                <h3>Dynamic of diamond derivatives for last year</h3>
                                <img src="/theme/app/img/graph03.png" alt="graph">
                            </div>-->

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

                                        <div ng-controller="HistoryTradeOrderController as vm">
                                        <table class="diamont-table">
                                            <thead>
                                            <tr>
                                                <th>Amount</th>
                                                <th>Price</th>
                                                <th>Time</th>
                                            </tr>
                                            </thead>
                                            <tbody>

                                            <tr ng-repeat="tradeOrder in vm.historyTradeOrders track by tradeOrder.id">
                                                <td>{{tradeOrder.initialAmount | number : 4}}</td>
                                                    <td ng-if="tradeOrder.tradeOrderDirection == 'BUY'" class="up">
                                                        {{tradeOrder.price | number : 4 }}

                                                            <img ng-src="/theme/app/img/up.png" alt="up">

                                                    </td>
                                                    <td ng-if="tradeOrder.tradeOrderDirection == 'SELL'" class="down">
                                                        {{tradeOrder.price | number : 4}}
                                                            <img ng-src="/theme/app/img/down.png" alt="down">

                                                    </td>
                                                <td>{{tradeOrder.executionDate | date:'dd/MM HH:mm:ss' }}</td>
                                            </tr>
                                            </tbody>
                                        </table>
                                        </div>
                                    </div>
                                </div>

                                <div ng-controller="BookOrderController as vm" class="trades-tab" id="tab04">
                                        <div class="table-container">
                                            <table class="diamont-table diamont-table--pdb">
                                                <thead>
                                                <tr>
                                                    <th>Amount</th>
                                                    <th>Price</th>
                                                    <th>Time</th>
                                                </tr>
                                                </thead>
                                                <tbody>

                                                <tr ng-repeat="tradeOrder in vm.bookOrder.sellOrders track by tradeOrder.id">
                                                    <td style="padding-right: 10px;">{{tradeOrder.amount | number : 4}}</td>
                                                    <td style="padding-right: 10px" class="down">{{tradeOrder.price | number : 4}}</td>
                                                    <td>{{tradeOrder.creationDate | date:'dd/MM HH:mm:ss' }}</td>

                                                    <!--
                                                    <td style="width: 114px;">{{tradeOrder.initialAmount | number : 4}}</td>
                                                    <td style="width: 50px;" class="down">{{tradeOrder.price | number : 4}}</td>
                                                    <td>{{tradeOrder.creationDate | date:'dd/MM HH:mm:ss' }}</td>-->
                                                </tr>


                                                <thead style="border-top: 1px solid #e7e7e7;">
                                                <tr>
                                                    <th>Spread</th>
                                                    <th></th>
                                                    <th>{{vm.spread | number : 7}}</th>
                                                </tr>
                                                </thead>


                                                <tr ng-repeat="tradeOrder in vm.bookOrder.buyOrders track by tradeOrder.id">
                                                    <td>{{tradeOrder.amount | number : 4}}</td>
                                                    <td class="up">{{tradeOrder.price | number : 4 }}</td>
                                                    <td>{{tradeOrder.creationDate | date:'dd/MM HH:mm:ss' }}</td>
                                                </tr>

                                                </tbody>
                                            </table>
                                        </div>
                                    <!--
                                        <div class="table-container table-container--btp">
                                            <table class="diamont-table">
                                                <thead>
                                                <tr>
                                                    <th>Spread</th>
                                                    <th></th>
                                                    <th></th>
                                                    <th>{{vm.bookOrder.sellOrders[0].price - vm.bookOrder.buyOrders[0].price}}</th>
                                                </tr>
                                                </thead>
                                                <tbody>

                                                <tr ng-repeat="tradeOrder in vm.bookOrder.sellOrders track by tradeOrder.id">
                                                    <td style="width: 114px;">{{tradeOrder.initialAmount | number : 4}}</td>
                                                    <td style="width: 50px;" class="down">{{tradeOrder.price | number : 4}}</td>
                                                    <td>{{tradeOrder.creationDate | date:'dd/MM HH:mm:ss' }}</td>
                                                </tr>

                                                </tbody>
                                            </table>
                                        </div>-->
                                </div>
                            </div>
                        </div>
                        <div class="sep-cab-page__side sep-cab-page__bottom-right">
                            <ul class="sep-cab-tabs sep-cab-tabs--tables">
                                <li class="active">
                                    <a href="#tab01">Open Orders</a>
                                </li>
                                <li>
                                    <a href="#tab02">History Orders</a>
                                </li>
                                <li>
                                    <a href="#tab010">My {{cc.config.assetName}}</a>
                                </li>
                            </ul>
                            <div class="sep-cab-tabs-container">
                                <div id="tab01" ng-controller="LiveTradeOrderController as vm" class="sep-cab-tab"  >
                                    <div class="table-container">
                                        <div>
                                            <table class="diamont-table">
                                            <thead>
                                            <tr>

                                                <th>Name</th>
                                                <th>Price</th>
                                                <th>Amount</th>
                                                <th>Executed sum</th>
                                                <th>t creation </th>
                                                <th>Status</th>
                                                <th>Type</th>
                                                <th>Action</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr ng-repeat="tradeOrder in vm.liveTradeOrders.content">

                                                <td>{{tradeOrder.diamond.name}}</td>
                                                <td>{{tradeOrder.price | number : 4 }}</td>
                                                <td>{{tradeOrder.initialAmount | number : 4}}</td>
                                                <td>{{tradeOrder.executionSum | number : 2}}</td>
                                                <td>{{tradeOrder.creationDate | date:'MM/dd HH:mm:ss' }}</td>
                                                <td>{{tradeOrder.traderOrderStatus | lowercase}}</td>
                                                <td>{{tradeOrder.tradeOrderDirection | lowercase}} {{tradeOrder.tradeOrderType | lowercase}}</td>
                                                <!--
                                                <td>{{tradeOrder.amount}}</td>
                                                -->

                                                <td><a ng-if="tradeOrder.traderOrderStatus == 'CREATED'" href="#" class="button black" ng-click="$event.preventDefault(); cancelTradeOrder(tradeOrder)">Cancel</a></td>
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
                                            -->
                                            </tbody>
                                        </table>
                                        </div>
                                    </div>
                                    <a href="#" ng-click="getPreviousLiveOrders(vm.liveTradeOrders.number)" class="sep-cab-tab__more">Previous orders</a>
                                </div>
                                <div class="sep-cab-tab" id="tab02">
                                    <div class="table-container" ng-controller="TradeOrderAccountController as vm" ng-cloak>
                                        <div>
                                            <table class="diamont-table">
                                                <thead>
                                                <tr>
                                                    <th>Name</th>
                                                    <th>t created</th>
                                                    <th>t executed</th>
                                                    <th>Amount</th>
                                                    <th>Price</th>
                                                    <th>Sum</th>
                                                    <th>Status</th>
                                                    <th>Type</th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <tr ng-repeat="tradeOrder in vm.accountHistoryTradeOrders.content">
                                                    <td>{{tradeOrder.diamond.name}}</td>
                                                    <td>{{tradeOrder.creationDate | date:'dd/MM HH:mm:ss' }}</td>
                                                    <td>{{tradeOrder.executionDate | date:'dd/MM HH:mm:ss' }}</td>
                                                    <td>{{tradeOrder.initialAmount | number : 2}}</td>
                                                    <td><span ng-if="tradeOrder.tradeOrderType == 'LIMIT' ">{{tradeOrder.price | number : 2}}</span></td>
                                                    <td>{{tradeOrder.executionSum | number : 2}}</td>
                                                    <td>{{tradeOrder.traderOrderStatus | lowercase}}</td>
                                                    <td>{{tradeOrder.tradeOrderDirection | lowercase}} {{tradeOrder.tradeOrderType | lowercase}}</td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                        <a href="#" ng-click="getPreviousAccountHistoryOrders(vm.accountHistoryTradeOrders.number)" class="sep-cab-tab__more">Previous orders</a>
                                    </div>
                                </div>
                                <div class="sep-cab-tab" id="tab010">
                                    <div class="table-container">

                                        <div ng-controller="BalanceController as vm">
                                            <table class="diamont-table">
                                                <thead>
                                                <tr>
                                                    <th>Name</th>
                                                    <!--
                                                    <th>Цена</th>
                                                    <th>t покупки</th>
                                                    <th>Статус</th>
                                                    <th>Цена продажи</th>
                                                    -->
                                                    <th>Amount</th>
                                                    <!--
                                                    <th>Action</th>
                                                    -->
                                                </tr>
                                                </thead>
                                                <tbody>

                                                <tr ng-repeat="balance in vm.balances track by balance.id" ng-click="chooseBalance(balance.currency)">
                                                    <!--
                                                    <td>{{stock.id}}</td>
                                                    <td>{{stock.diamond.id}}</td>
                                                    -->
                                                    <td>{{balance.currency}}</td>
                                                    <td>{{balance.amount | number}}</td>
                                                    <td>
                                                        <a href="#">Choose</a>
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
                                                </tr>-->
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                    <!--
                                    <a href="#" class="sep-cab-tab__more">Показать предыдущие позиции</a>-->
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
    <#include "parts/footer.ftl">
</div>
</div>

<#include "parts/bootom-scripts.ftl">

</body>
</html>
