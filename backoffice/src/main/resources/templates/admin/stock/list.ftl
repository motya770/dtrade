<#include "/admin/header.ftl">

<h1>Assets</h1>
<div class="table-responsive">
    <table class="table">
        <tbody>
        <tr>
            <th>Id</th>
            <th>Asset Id</th>
            <th>Asset Name</th>
            <th>Account Id</th>
            <th>Account Name</th>
            <th>Amount</th>
        </tr>
        <#if stocks??>
            <#list stocks as stock>
            <tr>
                <td>${stock.id}</td>
                <td>${stock.diamond.id}</td>
                <td>${stock.diamond.name}</td>
                <td><a href="/admin/account/show?id=${stock.account.id}"> ${stock.account.id}</a> </td>
                <td>${stock.account.mail}</td>
                <td>${stock.amount}</td>
            </tr>
            </#list>
        </#if>
        </tbody>
    </table>
</div>

<#include "/admin/footer.ftl">


