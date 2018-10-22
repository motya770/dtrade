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

                <li ng-show="vm.account" ng-class="{ active: isActive('/referral')}">
                    <a href="/trade#!/referral">Referral</a>
                </li>

            <li ng-show="vm.account">
                    <form action="https://www.coinpayments.net/index.php" method="post">
                        <input type="hidden" name="cmd" value="_pay">
                        <input type="hidden" name="reset" value="1">
                        <input type="hidden" name="merchant" value="1fb3cd572acffff43b1c0356d5429f1c">
                        <input type="hidden" name="item_name" value="Diaminds Deposit">
                        <input type="hidden" name="currency" value="USD">
                        <input type="hidden" name="first_name" value="Trading platform">
                        <input type="hidden" name="last_name" value="user">
                        <input type="hidden" name="amountf" value="200">
                        <input type="hidden" name="quantity" value="1">
                        <input type="hidden" name="allow_quantity" value="0">
                        <input type="hidden" name="allow_amount" value="1">
                        <input type="hidden" name="want_shipping" value="0">
                        <input type="hidden" name="success_url" value="www.exchange1.io/trade">
                        <input type="hidden" name="allow_extra" value="0">
                        <input type="hidden" name="email" value="{{vm.account.mail}}">
                        <md-button class="md-raised md-primary" type="submit" >Deposit</md-button>
                    </form>
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