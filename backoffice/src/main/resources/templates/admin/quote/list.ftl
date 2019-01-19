<#include "/admin/header.ftl">

<h1>Assets</h1>

<div class="table-responsive">
    <table class="table">
        <tr>
            <th>ID</th>
            <th>Asset Id</th>
            <th>Asset Name</th>
            <th>Price</th>
             <th>Ask</th>
             <th>Bid</th>
            <th>Date</th>
            <th>Type</th>
        </tr>
        <tbody>
        <#if quotes??>
            <#list quotes.content as quote>
            <tr>
                <td>${quote.id}</td>
                <td>${quote.diamond.id}</td>
                <td>${quote.diamond.name}</td>
                <td>${(quote.price)!""}</td>
                <td>${(quote.ask)!""}</td>
                <td>${(quote.bid)!""}</td>
                <td>${quote.time?number_to_datetime}</td>
                <td>${quote.quoteType}</td>
            </tr>
            </#list>
        </#if>
        </tbody>
    </table>
</div>

<#include "/admin/footer.ftl">


