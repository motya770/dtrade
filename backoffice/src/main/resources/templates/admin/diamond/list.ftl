<#include "/admin/header.ftl">

<h1>Assets</h1>
<a href="/admin/diamond/new-entity">Create Item</a>

<div class="table-responsive">
    <table class="table">
        <tbody>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Price</th>
            <th>Owner</th>
            <!-- do not remove
            <th>Type</th>
            <th>Status</th>
            <th>Carats</th>
            <th>Clarity</th>
            -->
            <th>Asset Type</th>
            <th>Total Stock Amount</th>
            <th>Robo High End</th>
            <th>Robo Low End</th>
            <th>Robo Last Updated</th>
            <th>Robo Max Amount</th>
            <th>Currency</th>
            <th>Base Currency</th>
        </tr>
        <#if diamonds??>
            <#list diamonds as diamond>
            <tr>
                <td> <a href="/admin/diamond/edit?id=${diamond.id}"> ${diamond.id}</a></td>
                <td>${diamond.name}</td>
                <td>${diamond.price}</td>
                <td><a href="/admin/account/show?id=${diamond.account.id}"> ${diamond.account.id}</a> </td>
                <td>${diamond.assetType!""}</td>
                <!--
                <td>${diamond.diamondType}</td>
                <td>${diamond.diamondStatus}</td>
                <td>${diamond.carats}</td>
                <td>${diamond.clarity}</td>
                -->
                <td>${diamond.totalStockAmount}</td>
                <td>${diamond.roboHighEnd!""}</td>
                <td>${diamond.roboLowEnd!""}</td>
                <#if diamond.lastRoboUpdated??>
                    <td>${diamond.lastRoboUpdated?number_to_datetime}</td>
                <#else>
                    <td></td>
                </#if>

                <#if diamond.roboMaxAmount??>
                    <td>#{diamond.roboMaxAmount!""}</td>
                <#else>
                    <td></td>
                </#if>
                <td>${diamond.currency}</td>
                <td>${diamond.baseCurrency}</td>
            </tr>
            </#list>
        </#if>
        </tbody>
    </table>
</div>

<#include "/admin/footer.ftl">


