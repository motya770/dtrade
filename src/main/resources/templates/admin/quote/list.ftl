<#include "/admin/header.ftl">

<h1>Diamonds</h1>

<div class="table-responsive">
    <table class="table">
        <th>
            <td>ID</td>
            <td>Diamond Id</td>
            <td>Diamond Name</td>
            <td>Price</td>
            <td>Date</td>
            <td>Type</td>
        </th>
        <tbody>
        <#if quotes??>
            <#list quotes.content as quote>
            <tr>
                <td>${quote.id}</td>
                <td>${quote.diamond.id}</td>
                <td>${quote.diamond.name}</td>
                <td>${quote.price}</td>
                <td>${quote.time?number_to_datetime}</td>
                <td>${quote.quoteType}</td>
            </tr>
            </#list>
        </#if>
        </tbody>
    </table>
</div>

<#include "/admin/footer.ftl">


