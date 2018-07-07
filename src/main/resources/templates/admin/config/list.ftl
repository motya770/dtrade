<#include "/admin/header.ftl">

<h1>Configs</h1>
<div class="table-responsive">
    <table class="table">
        <tbody>
        <tr>
            <th>Id</th>
            <th>Asset Type</th>
            <th>Asset Name</th>
            <th>Asset Name Listing</th>
        </tr>
        <#if configs??>
            <#list configs as config>
            <tr>
                <td> <a href="/admin/config/edit?id=${config.id}"> ${config.id}</a></td>
                <td>${config.assetType}</td>
                <td>${config.assetName}</td>
                <td>${config.assetNameForListing}</td>
            </tr>
            </#list>
        </#if>
        </tbody>
    </table>
</div>

<#include "/admin/footer.ftl">


