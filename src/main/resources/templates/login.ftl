<!DOCTYPE html>
<html ng-app="diamondApp">
<head>
    <#include "parts/head.ftl">
</head>

<body class="page">

<!-- Custom HTML -->
<div class="site" ng-cloak="" ng-controller="ConfigController as cc">
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



