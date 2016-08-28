<#include "/admin/header.ftl">

<h1>Accounts</h1>
<div class="table-responsive">
    <table class="table">
        <tbody>
        <#if accounts??>
            <#list accounts as account>
            <tr>
                <td>${account.id}</td>
                <td>${account.mail}</td>
                <td>${account.balance}</td>
            </tr>
            </#list>
        </#if>
        </tbody>
    </table>
</div>

<#include "/admin/footer.ftl">


