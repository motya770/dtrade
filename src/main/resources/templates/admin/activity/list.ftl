<#include "/admin/header.ftl">

<h1>Activities</h1>
<div class="table-responsive">
    <table class="table">
        <tbody>
        <#if accounts??>
            <#list activities as activity>
            <tr>
                <td>${activity.id}</td>
                <td>${activity.seller.mail}</td>
                <td>${activity.buyer.mail}</td>
                <td>${activity.diamond.id}</td>
                <td>${activity.diamond.name}</td>
                <td>${activity.sum}</td>
                <td>${activity.date}</td>
            </tr>
            </#list>
        </#if>
        </tbody>
    </table>
</div>

<#include "/admin/footer.ftl">


