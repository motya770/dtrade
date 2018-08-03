<!DOCTYPE html>
<html ng-app="diamondApp">

<head>

    <#include "parts/head.ftl">


</head>

<body class="page"  ng-cloak="">

<!-- Custom HTML -->
<div class="site" ng-controller="ConfigController as cc">

    <#include "parts/caption.ftl">
    <#include "parts/menu.ftl">

    <main class="content">

        <#include "parts/portfolio.ftl">

        <div class="def-cab-page">
            <div class="def-cab-page__inner">
                <div class="def-cab-page__content">
                    <h2 class="def-cab-page__heading">{{cc.config.assetName}}<span ng-if="cc.config.assetType!='CRYPTO'">shares</span></h2>

                    <div ng-controller="DiamondController as vm" ng-cloak>
                        <div class="table-container">
                        <table class="diamont-table">
                            <thead>
                            <tr>
                                <th>Name</th>
                                <th>Initial price</th>
                                <!--
                                <th>Количество</th>
                                -->
                                <!--
                                <th>Type</th>
                                -->
                                <th>Status</th>
                                <!--
                                <th>Carats</th>
                                <th>Clarity</th>
                                <th>Cut</th>
                                <th>Color</th>
                                <th>Σ stocks</th>-->
                                <!--
                                <th></th>
                                -->
                            </tr>
                            </thead>
                            <tbody>
                            <tr ng-repeat="diamond in vm.diamonds">
                                <td>{{diamond.name}} <!--<br>ISIN: {{diamond.id}}--></td>
                                <td>{{diamond.price | number }}</td>
                                <!--
                                <td>{{diamond.amount}}</td>
                                -->
                                <!--
                                <td>{{diamond.diamondType | lowercase}}</td>
                                -->
                                <td>{{diamond.diamondStatus | lowercase}}</td>
                                <!--
                                <td>{{diamond.carats}}</td>
                                <td>{{diamond.clarity}}</td>
                                <td>{{diamond.cut | lowercase}}</td>
                                <td>{{diamond.color}}</td>
                                <td>{{diamond.totalStockAmount | number}}</td>
                                -->
                                <!--
                                <td>
                                    <a href="#" data-open="1">More</a>
                                </td>
                                -->
                            </tr>
                            <!--
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
                            </tr>-->
                            <!--
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
                            </tr>-->
                            </tbody>
                        </table>
                    </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
    <#include "parts/footer.ftl">
</div>

<#include "parts/bootom-scripts.ftl">
<script src="/content/js/app/diamond.controller.js"></script>

</body>
</html>
