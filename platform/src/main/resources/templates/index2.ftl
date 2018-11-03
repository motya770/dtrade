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
   <div  ng-controller="ConfigController as cc">
    <#include "parts/caption.ftl">
    <#include "parts/menu.ftl">
   </div>

    <main class="content">
        <#include "parts/portfolio.ftl">
        <div ng-view></div>
    </main>
    <#include "parts/footer.ftl">
    </div>
</div>

<#include "parts/bootom-scripts.ftl">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<!-- Go to www.addthis.com/dashboard to customize your tools -->
<script type="text/javascript" src="//s7.addthis.com/js/300/addthis_widget.js#pubid=ra-5bde07ec854a3731"></script>

</body>
</html>
