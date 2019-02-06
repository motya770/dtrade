<#include "/admin/header.ftl">

<h1>Accounts</h1>
<div class="table-responsive">
    <table class="table">
        <tr>
            <th>ID</th>
            <th>Mail</th>
            <th>USD</th>
            <th>BTC</th>
            <th>ETH</th>
            <th>Resend referall</th>

        </tr>
        <tbody>

        <#if accounts??>
            <#list accounts.content as account>
            <tr>
                <td><a href="/admin/account/show?id=${account.id}"> ${account.id}</a></td>
                <td>${account.mail}</td>
                <td>${(account.balance.usdAmount)!""}</td>
                <td>${(account.balance.bitcoinAmount)!""}</td>
                <td>${(account.balance.etherAmount)!""}</td>
                <td><a href="/admin/account/resend-email?accountId=#{account.id}">Resend</a></td>
            </tr>
            </#list>

            <a href="/admin/account/list?pageNumber=${accounts.number + 1}">
            ${accounts.number + 1}
            </a>
        </#if>
        </tbody>
    </table>
</div>

<#include "/admin/footer.ftl">


