<#include "/admin/header.ftl">

<h1>Accounts</h1>
<div class="table-responsive">
    <table class="table">
        <tr>
            <th>ID</th>
            <th>Mail</th>
            <th>Balance</th>
        </tr>
        <tbody>

        <#if accounts??>
            <#list accounts as account>
            <tr>
                <td><a href="/admin/account/show?id=${account.id}"> ${account.id}</a></td>
                <td>${account.mail}</td>
                <td>${(account.balance)!""}</td>
            </tr>
            </#list>
        </#if>
        </tbody>
    </table>
</div>

<#include "/admin/footer.ftl">


