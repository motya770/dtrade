<#include "/admin/header.ftl">

<h1>Diamonds</h1>
<div class="table-responsive">
    <table class="table">
        <tbody>
        <tr>
            <th>Id</th>
            <th>Diamond Id</th>
            <th>Diamond Name</th>
            <th>Account Id</th>
            <th>Account Name</th>
            <th>Amount</th>
            <th>Initial Amount</th>
            <th>Price</th>
            <th>Creation Date</th>
            <th>Status</th>
            <th>Type</th>
        </tr>
        <#if tradeOrders??>
            <#list tradeOrders.content as tradeOrder>
            <tr>
                <td>${tradeOrder.id}</td>
                <td>${tradeOrder.diamond.id}</td>
                <td>${tradeOrder.diamond.name}</td>
                <td><a href="/admin/account/show?id=${tradeOrder.account.id}"> ${tradeOrder.account.id}</a> </td>
                <td>${tradeOrder.account.mail}</td>
                <td>${tradeOrder.amount}</td>
                <td>${tradeOrder.initialAmount}</td>
                <td>${tradeOrder.price}</td>
                <td>${tradeOrder.creationDate?number_to_datetime}</td>
                <td>${(tradeOrder.traderOrderStatus)!""}</td>
                <td>${(tradeOrder.tradeOrderDirection)!""}</td>
            </tr>
            </#list>

            <a href="/admin/tradeorder/list?pageNumber=${tradeOrders.number + 1}">
            ${tradeOrders.number + 1}
            </a>
        </#if>
        </tbody>
    </table>
</div>

<#include "/admin/footer.ftl">


