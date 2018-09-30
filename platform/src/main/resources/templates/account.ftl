<!DOCTYPE html>
<html ng-app="diamondApp">

<head>
    <#include "parts/head.ftl">
</head>

<body class="page">

<!-- Custom HTML -->
<div class="site" ng-controller="ConfigController as cc">
    <#include "parts/caption.ftl">
    <#include "parts/menu.ftl">

    <main class="content">
        <#include "parts/portfolio.ftl">
    </main>
    <#include "parts/footer.ftl">
</div>

<#include "parts/bootom-scripts.ftl">

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