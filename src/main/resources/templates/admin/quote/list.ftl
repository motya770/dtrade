<#include "/admin/header.ftl">

<h1>Diamonds</h1>
<div class="table-responsive">
    <table class="table">
        <tbody>
        <#if quotes??>
            <#list quotes as quote>
            <tr>
                <td>${quote.id}</td>
                <td>${quote.diamond.id}</td>
                <td>${quote.diamond.name}</td>
                <td>${quote.value}</td>
                <td>${quote.time}</td>
            </tr>
            </#list>
        </#if>
        </tbody>
    </table>
</div>

<#include "/admin/footer.ftl">


