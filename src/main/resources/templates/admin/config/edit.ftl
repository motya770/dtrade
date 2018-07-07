<#import "/spring.ftl" as spring/>

<#include "/admin/header.ftl">

<h1>Create Config</h1>
<div style="width: 500px;">
    <form method="post" action="/admin/config/save">
    <@spring.bind "config"/>
        <div>
            <input type="hidden" name="id" value="${(config.id)!""}">
        </div>
        <div class="form-group">
            <label for="name">Asset Name</label>
        ${(config.assetName)!""}
            <input type="text" class="form-control" name="assetName" id="assetName" placeholder="assetName" value="${(config.assetName!"")}">
        </div>

        <div class="form-group">
            <label for="name">Asset Name Listing</label>
        ${(config.assetNameForListing)!""}
            <input type="text" class="form-control" name="assetNameForListing" id="assetNameForListing" placeholder="assetNameForListing" value="${(config.assetNameForListing!"")}">
        </div>


        <div class="radio">
            <label>Asset Type</label><br/>
        <#assign stringStatusValue = (config.assetType)!"" />
        <@spring.formRadioButtons "config.assetType", assetTypes, ""/><br><br>
        </div>


        <div class="form-group">
            <label for="name">Active</label>

            <#if config.active == true>
            <input type="checkbox" class="form-control" name="active" id="active" placeholder="active"  checked>
            <#else>
            <input type="checkbox" class="form-control" name="active" id="active" placeholder="active">
            </#if>
        </div>

        <button type="submit" class="btn btn-default">Submit</button>
    </form>

</div>

<#include "/admin/footer.ftl">


