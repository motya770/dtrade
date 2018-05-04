<nav class="subnav">
    <div class="subnav__inner">
        <ul ng-controller="MenuController as vm" class="subnav__list">
            <li ng-class="getClass('/trade')">
                <a href="/trade">Trade</a>
            </li>
            <li ng-class="getClass('/diamonds')" >
                <a href="/diamonds">Diamonds</a>
            </li>
            <li ng-show="vm.account" ng-class="getClass('/account')">
                <a href="/account">My account</a>
            </li>
        </ul>
    </div>
</nav>