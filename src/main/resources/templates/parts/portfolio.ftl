<div class="cabtop-info">
    <div ng-controller="AccountController as vm" class="cabtop-info__inner">
        <div class="cabtop-info__content">
                <span class="cabtop-info__ico"></span>
                <div class="cabtop-info__text">
                    <h3>Аккаунт:  {{vm.account.mail}}</h3>
                    <p><span class="accent">Балланс</span>  <span class="accent">{{vm.account.balance}}  $.</span></p>

                    <!--
                    <h3>Портфель акций</h3>
                    <p><span class="accent">34 акции</span> на общую сумму <span class="accent">145 000 руб.</span></p>
                    -->
                </div>
        </div>
    </div>
</div>
