<nav class="subnav">
    <div class="subnav__inner">
        <ul ng-controller="MenuController as vm" class="subnav__list">
                <li ng-class="getClass('/simple')">
                    <a href="/simple">Simple</a>
                </li>
                <li ng-class="getClass('/trade')">
                    <a href="/trade">Advanced</a>
                </li>
                <li ng-class="getClass('/diamonds')" >
                    <a href="/{{cc.config.assetType|lowercase}}">{{cc.config.assetName}}</a>
                </li>
                <li ng-show="vm.account" ng-class="getClass('/account')">
                    <a href="/account">My account</a>
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