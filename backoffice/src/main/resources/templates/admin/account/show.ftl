<#import "/spring.ftl" as spring/>

<#include "/admin/header.ftl">

<h1>Account</h1>
<div style="width: 500px;">

    <div>
        Id ${(account.id)!""}
    </div>
    <div>
        Mail ${(account.mail)!""}
    </div>
    <div>
        Balance ${(account.balance)!""}
    </div>

    <div>
        Owned Assets
    </div>

    <div>
        Assets for Sale
    </div>

    <div>
        Balance Activity
    </div>

    <div>
        Asset Trade Activity
    </div>
</div>

<#include "/admin/footer.ftl">


