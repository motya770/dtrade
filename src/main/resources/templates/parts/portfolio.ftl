<div class="cabtop-info" style="padding-top: 10px; padding-bottom: 10px;">
    <div ng-controller="AccountController as vm" class="cabtop-info__inner">
        <div ng-show="vm.account" class="cabtop-info__content" ng-cloak>
                <span class="cabtop-info__ico"></span>
                <div class="cabtop-info__text">
                    <h3>Account:  {{vm.account.mail}}</h3>
                    <p><span class="accent">Balance</span>
                        <span class="accent" ng-repeat="balance in vm.account.balance">{{balance.amount | number}}  $.</span>
                    </p>
                </div>
        </div>
        <!--
                    <h3>Портфель акций</h3>
                    <p><span class="accent">34 акции</span> на общую сумму <span class="accent">145 000 руб.</span></p>

        -->
    </div>
</div>
