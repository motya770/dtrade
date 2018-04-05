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

        <div class="sep-cab-page">
            <div class="sep-cab-page__inner">
                <div class="sep-cab-page__content">
                    <div class="sep-cab-page__top">
                        <div class="sep-cab-page__side sep-cab-page__left">
                            <div class="sep-cab-page__heading">
                                <h2>Список бриллиантов</h2>
                                <p>Все акции бриллиантов в работе</p>
                            </div>
                            <!--
                            <form class="searchform">
                                <input type="text" class="searchform__inp" placeholder="Введите имя бриллианта">
                                <button class="searchform__btn">Найти</button>
                            </form>
                            -->

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
                                    <!--
                                    <li class="active">
                                        <a href="#tab05">Market</a>
                                    </li>-->
                                    <li class="active">
                                        <a href="#tab06">Limit</a>
                                    </li>
                                </ul>


                                <!--
                                <div ng-controller="BidderController as vm">
                                    <div class="pull-left button-block">
                                        <div class="buy-block">
                                            <div>
                                                Do You want to buy {{vm.buyDiamond.name}} ({{vm.buyDiamond.id}})?
                                            </div>

                                            <div class="price">PRICE:</div>
                                            <div class="btn-group size">
                                                <button class="btn">-</button>
                                                <input ng-model="vm.buyOrder.price" type="text" class="btn"/>
                                                <button class="btn">+</button>
                                            </div>

                                            <div class="amount">AMOUNT: <input ng-model="vm.buyOrder.amount"></input></div>
                                            <input type="hidden" ng-model="vm.buyOrder.tradeOrderType" ng-init="vm.buyOrder.tradeOrderType='BUY'" />

                                            <a class="button black" href="#" ng-click="$event.preventDefault(); createTradeOrder(vm.buyOrder, vm.buyDiamond)">BUY ORDER</a>
                                        </div>

                                        <div class="sell-block">
                                            <div>
                                                Open {{vm.sellDiamond.name}} ({{vm.sellDiamond.id}}) For Sale?
                                            </div>

                                            <div class="price">PRICE:</div>
                                            <div class="btn-group size">
                                                <button class="btn">-</button>
                                                <input ng-model="vm.sellOrder.price" type="text" class="btn"/>
                                                <button class="btn">+</button>
                                            </div>
                                            <div class="amount">AMOUNT: <input ng-model="vm.sellOrder.amount"></input></div>

                                            <input type="hidden" ng-model="vm.sellOrder.tradeOrderType" ng-init="vm.sellOrder.tradeOrderType='SELL'" />
                                            <a href="#" class="button black" ng-click="$event.preventDefault(); createTradeOrder(vm.sellOrder, vm.sellDiamond)">SELL ORDER</a>
                                        </div>

                                    </div>
                                </div>
                                -->

                                <div ng-controller="BidderController as vm">
                                    <div class="form-tabs">
                                        <!--
                                    <form class="form-tabs__tab" id="tab05">
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
                                            <h3>Total (USD)</h3>
                                            <button class="form-tabs__subm" ng-click="alert('test'); createTradeOrder(vm.tradeOrder, vm.diamond);">Place order</button>
                                        </div>
                                    </form>-->
                                    <form class="form-tabs__tab" id="tab06">
                                        <div class="form-tabs__boxes">
                                            <div class="form-tabs__info-top diamond-bid-name">
                                                <div>{{vm.diamond.name}}</div>
                                                <p>{{vm.diamond.id}}, {{vm.diamond.diamondType}},
                                                {{vm.diamond.carats}}, {{vm.diamond.clarity}}</p>
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
                                            <input type="text" ng-model="vm.tradeOrder.amount" value="0.00" id="input01">
                                        </div>
                                        <div class="form-tabs__row">
                                            <label for="input02">Limit price</label>
                                            <input type="text" ng-model="vm.tradeOrder.price"  value="0.00" id="input02" class="pdr">
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
                                            <h3>Total (USD)</h3>
                                            <button class="form-tabs__subm" onclick="alert('123');" ng-click="alert('test1'); createTradeOrder(vm.tradeOrder, vm.diamond)">Place order</button>
                                        </div>
                                    </form>
                                </div>
                                </div>


                            </div>
                            <!--
                            <div class="sep-cab-page__activity">
                                <div class="sep-cab-page__heading">
                                    <h2>Общая активность</h2>
                                    <p>Средние показатели роста акций за год</p>
                                </div>
                                <div class="activity-graph">
                                    <img src="/theme/app/img/graph02.png" alt="graph">
                                </div>
                            </div>-->
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
                            <!--
                            <div class="sub-trade-graph">
                                <h3>Динамика акций бриллианта за последний год</h3>
                                <img src="/theme/app/img/graph03.png" alt="graph">
                            </div>
                            -->
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
                                                <td>{{tradeOrder.initialAmount}}</td>
                                                <td class="up">
                                                    {{tradeOrder.price}}
                                                    <img src="/theme/app/img/up.png" alt="up">
                                                </td>
                                                <td>{{tradeOrder.creationDate | date:'yyyy-MM-dd HH:mm:ss' }}</td>
                                            </tr>

                                            <!--
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
                                            -->
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
                                            <!--
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
                                            </tr>-->
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
                                                <td>{{tradeOrder.amount}}</td>
                                                <td class="down">{{tradeOrder.price}}</td>
                                                <td>{{tradeOrder.creationDate | date:'dd/MM HH:mm:ss' }}</td>
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
    <#include "parts/footer.ftl">

</div>
<#include "parts/bootom-scripts.ftl">


</body>
</html>
