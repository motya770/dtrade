<header class="header header--page">
    <div class="header__inner">
        <div class="header__logo">
            <a href="/">Diaminds<br><small>Financial diamond exchange</small></a>
        </div>
        <div ng-cloak class="header__right" ng-controller="AccountController as vm" >
            <a ng-hide="vm.account" href="/login-page" class="btn btn--darken">Login</a>
            <a ng-show="vm.account" href="/logout" class="btn btn--darken">Logout</a>
        </div>
    </div>
</header>