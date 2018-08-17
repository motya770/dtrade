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
                    <div ng-controller="CoinController as vm">
                        {{vm.coin}}
                    </div>
                </div>
            </div>
        </div>
    </main>
<#include "parts/footer.ftl">
</div>

<#include "parts/bootom-scripts.ftl">
<script src="/content/js/app/diamond.controller.js"></script>
<script src="/content/js/app/coin.controller.js"></script>

</body>
</html>
