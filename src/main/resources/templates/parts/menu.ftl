<nav class="subnav">
    <div class="subnav__inner">
        <ul ng-controller="MenuController as vm" class="subnav__list">
                <li ng-class="{ active: isActive('/basic')}" >
                    <a href="/trade#!/basic">Basic</a>
                </li>
                <li ng-class="{ active: isActive('/trade')}">
                    <a href="/trade#!/trade">Advanced</a>
                </li>
                <li ng-class="{ active: isActive('/coins')}" >
                    <a href="/trade#!/coins">Coins</a>
                    <!--
                    <a href="/{{cc.config.assetType|lowercase}}">{{cc.config.assetName}}</a>
                    -->
                </li>
                <li ng-show="vm.account" ng-class="{ active: isActive('/account')}">
                    <a href="/trade#!/account">My account</a>
                </li>

            <!--
                <span ng-controller="AccountController as ac" >
                    <li ng-hide="ac.account" >
                        <a  href="/login-page">Login</a>
                    </li>
                    <li ng-show="ac.account">
                        <a href="/logout">Logout</a>
                    </li>
                </span>
                -->
        </ul>
    </div>
</nav>