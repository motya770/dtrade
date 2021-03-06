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

    </main>
    <#include "parts/footer.ftl">
</div>
</div>

<#include "parts/bootom-scripts.ftl">

</body>
</html>
