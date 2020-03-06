<header class="header header--page" style="background-image: linear-gradient(#0861E4, #0453DD);">
    <div class="header__inner">
        <div class="header__logo">
            <!--
            <a href="http://korono.io">
                <img style="height: 39px; width: 131px;" src="/theme/app/img/logo_korono.png"></img>
            </a>
            -->
        </div>
        <!--
        <div class="header__logo">
            <a href="https://korono.io">Korono<br><small>Free stock and crypto trading</small></a>
        </div>-->
        <div ng-cloak class="header__right" ng-controller="AccountController as vm" >
            <a ng-hide="vm.account" href="/trade#!/login-form" class="btn btn--darken">Login</a>
            <a ng-show="vm.account" href="/logout" class="btn btn--darken">Logout</a>
        </div>
    </div>
</header>


<!--
            <a href="/" class="header-logo"><img src="/ico/build/img/logo.png" alt="logo"></a>
 -->