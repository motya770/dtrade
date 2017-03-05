<#include "/admin/header.ftl">

<h1>Activities</h1>
<div class="table-responsive">
    <table class="table">
        <tbody>
        <#if diamondActivities??>
            <#list diamondActivities as activity>
            <tr>
                <td>${activity.id}</td>
                <td>
                <#if activity.seller??>
                   <a href="/admin/account/show?id=${activity.seller.id}">${activity.seller.mail}</a>
                </#if>
                </td>
                <td>
                <#if activity.buyer??>
                   <a href="/admin/account/show?id=${activity.buyer.id}"> ${activity.buyer.mail}</a>
                </#if>
                </td>
                <td><a href="/admin/diamond/show?id=${activity.diamond.id}"> ${activity.diamond.id}</a></td>
                <td>${activity.diamond.name}</td>
                <td>${activity.price}</td>

                <td>
                    <#if activity.date??>
                        ${activity.date?number_to_datetime}
                    </#if>
                </td>
            </tr>
            </#list>
        </#if>
        </tbody>
    </table>
</div>

<#include "/admin/footer.ftl">


