<header class="header header--page" style="background-color: #4C4D65;">
    <div class="header__inner">
        <div class="header__logo">

            <a href="/">Exchange1<br><small>Cryptocurrency exchange number 1</small></a>
        </div>
        <div ng-cloak class="header__right" ng-controller="AccountController as vm" >
            <a ng-hide="vm.account" href="/login-page" class="btn btn--darken">Login</a>
            <a ng-show="vm.account" href="/logout" class="btn btn--darken">Logout</a>
        </div>
    </div>
</header>


<!--
            <a href="/" class="header-logo"><img src="/ico/build/img/logo.png" alt="logo"></a>
 -->