<nav class="subnav">
    <div class="subnav__inner">
        <ul ng-controller="MenuController as vm" class="subnav__list">
            <li ng-class="getClass('/')">
                <a href="/">Торговать</a>
            </li>
            <li ng-class="getClass('/diamonds')" >
                <a href="/diamonds">Бриллианты</a>
            </li>
            <li ng-class="getClass('/account')">
                <a href="/account">Мой аккаунт</a>
            </li>
        </ul>
    </div>
</nav>