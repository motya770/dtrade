<#include "/admin/header.ftl">

<h1>Diamonds</h1>
<div class="table-responsive">
    <table class="table">
        <tbody>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Price</th>
            <th>Owner</th>
            <th>Type</th>
            <th>Status</th>
            <th>Carats</th>
            <th>Clarity</th>
            <th>Total Stock Amount</th>
        </tr>
        <#if diamonds??>
            <#list diamonds as diamond>
            <tr>
                <td> <a href="/admin/diamond/edit?id=${diamond.id}"> ${diamond.id}</a></td>
                <td>${diamond.name}</td>
                <td>${diamond.price}</td>
                <td><a href="/admin/account/show?id=${diamond.account.id}"> ${diamond.account.id}</a> </td>
                <td>${diamond.diamondType}</td>
                <td>${diamond.diamondStatus}</td>
                <td>${diamond.carats}</td>
                <td>${diamond.clarity}</td>
                <td>${diamond.totalStockAmount}</td>
            </tr>
            </#list>
        </#if>
        </tbody>
    </table>
</div>

<#include "/admin/footer.ftl">


