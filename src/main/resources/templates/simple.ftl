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
                                <h2>{{::cc.config.assetName}} listing</h2>
                                <p>All {{::cc.config.assetName}} are available</p>
                            </div>

                            <form class="searchform">
                                <input type="text" class="searchform__inp" ng-model="searchInputValue" placeholder="Enter the name of the {{::cc.config.assetName}}">
                                <button class="searchform__btn" ng-click="getAvailableByName();">Find</button>
                            </form>

                            <div class="type-diamond-table">
                                <div class="table-container">
                                    <div >
                                      <table class="diamont-table">
                                        <thead>
                                        <tr>
                                            <th>{{::cc.config.assetNameForListing}}</th>
                                            <th></th>
                                            <th>Price <!--<span class="inf">?</span>--></th>

                                        </tr>
                                        </thead>
                                        <tbody>

                                            <tr ng-repeat="diamond in ::vm.availableDiamonds" ng-click="chooseAvailableDiamond(diamond)">
                                                <td>{{::diamond.name}}
                                                    <small ng-if="::cc.config.assetType=='DIAMOND'">
                                                    {{::diamond.diamondType | diamondTypeFilter }},
                                                     GIA - {{::diamond.clarity}},
                                                     Cr - {{::diamond.carats}}
                                                    </small>
                                                </td>
                                                <td>
                                                    <div class="dialog-demo-content" layout="row" layout-wrap="" >
                                                         <input ng-if="::cc.config.assetType=='DIAMOND'" type="image" src="/theme/app/img/dia1.png" alt="Submit Form" class="md-primary md-raised" ng-click="showAdvanced( $event)" />
                                                         <input ng-if="::cc.config.assetType=='WINE'" style="height: 30px;" type="image" src="/theme/app/img/bottle.png" alt="Submit Form" class="md-primary md-raised" ng-click="showAdvanced( $event)" />
                                                    </div>
                                                </td>
                                                <td>{{diamond.askBidPair.avg | number : 6}} </td>
                                                <!--
                                                <td><span ng-if="::!diamond.hideTotalStockAmount">{{::diamond.totalStockAmount/10000000 | number}} mln </span></td>-->
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

                                        <tr ng-if="::cc.config.assetType=='DIAMOND'" style="width: 100%; background-color: #e7e7e7">
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
                                            <tr ng-if="::cc.config.assetType=='DIAMOND'" style="width: 100%; background-color: #e7e7e7">
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
                                            <tr ng-if="::cc.config.assetType=='DIAMOND'" style="width: 100%; background-color: #e7e7e7">
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


                                            <tr ng-if="::cc.config.assetType=='WINE'" style="width: 100%; background-color: #e7e7e7">
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


                                            <tr ng-if="::cc.config.assetType=='WINE'" style="width: 100%; background-color: #e7e7e7">
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
                                    <!--
                                    <li class="active">
                                        <a href="#tab05">Market</a>
                                    </li>-->
                                </ul>

                                <div ng-controller="BidderController as vm">
                                    <div class="form-tabs">
                                    <form class="form-tabs__tab" id="tab05">
                                        <div class="form-tabs__boxes">
                                            <div class="form-tabs__info-top diamond-bid-name">
                                                <div>{{vm.diamond.name}}</div>
                                                <p ng-if="::cc.config.assetType=='DIAMOND'" >{{vm.diamond.diamondType | diamondTypeFilter}},
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
                                </ul>
                            </div>
                            <div class="main-trade-graph" style="margin-top:0px; padding-bottom: 16px;">
                                <div class="form-tabs">
                                    <div ng-controller="ChartController as vm">
                                        <div  class="graph__tab" id="tab11" class="row" style="position: relative; width: 100%;">
                                            <div id="container" style="position: relative; width: 100%;"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="sep-cab-page__bottom">

                        <div class="sep-cab-page__side sep-cab-page__bottom-right">
                            <ul class="sep-cab-tabs sep-cab-tabs--tables">
                                <li class="active">
                                    <a href="#tab01">Open Orders</a>
                                </li>
                                <li>
                                    <a href="#tab010">My {{::cc.config.assetName}}</a>
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
                                                <td>{{tradeOrder.traderOrderStatus | lowercase}}</td>
                                                <td>{{tradeOrder.tradeOrderDirection | lowercase}} {{tradeOrder.tradeOrderType | lowercase}}</td>
                                                <td><a ng-if="tradeOrder.traderOrderStatus == 'CREATED'" href="#" class="button black" ng-click="$event.preventDefault(); cancelTradeOrder(tradeOrder)">Cancel</a></td>
                                            </tr>
                                            </tbody>
                                        </table>
                                        </div>
                                    </div>
                                    <a href="#" ng-click="getPreviousLiveOrders(vm.liveTradeOrders.number)" class="sep-cab-tab__more">Previous orders</a>
                                </div>
                                <div class="sep-cab-tab" id="tab010">
                                    <div class="table-container">

                                        <div ng-controller="BalanceController as vm">
                                            <table class="diamont-table">
                                                <thead>
                                                <tr>
                                                    <th>Name</th>

                                                    <th>Amount</th>
                                                </tr>
                                                </thead>
                                                <tbody>

                                                <tr ng-repeat="balance in vm.balances track by balance.id" ng-click="chooseBalance(balance.currency)">
                                                    <td>{{balance.currency}}</td>
                                                    <td>{{balance.amount | number}}</td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
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