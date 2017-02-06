<#include "/admin/header.ftl">

<h1>Activities</h1>
<div class="table-responsive">
    <table class="table">
        <tr>
            <th>Id</th>
            <th>Account</th>
            <th>Balance Activity Type</th>
            <th>Date</th>
        </tr>
        <tbody>
        <#if balanceActivities??>
            <#list balanceActivities as activity>
            <tr>
                <td>${activity.id}</td>
                <td><a href="/admin/account/show?id=${activity.account.id}"> ${activity.account.mail}</a></td>
                <td>${activity.balanceActivityType}</td>
                <td>${activity.amount}</td>
                <td>${activity.date}</td>

            </tr>
            </#list>
        </#if>
        </tbody>
    </table>
</div>

<#include "/admin/footer.ftl">


