<#include "/admin/header.ftl">

<h1>Activities</h1>
<div class="table-responsive">
    <table class="table">
        <tbody>
        <#if diamondActivities??>
            <#list diamondActivities as activity>
            <tr>
                <td>${activity.id}</td>
                <td><a href="/admin/account/show?id=${activity.seller.id}">${activity.seller.mail}</a></td>
                <td><a href="/admin/account/show?id=${activity.buyer.id}"> ${activity.buyer.mail}</a></td>
                <td><a href="/admin/diamond/show?id=${diamond.id}"> ${activity.diamond.id}</a></td>
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


