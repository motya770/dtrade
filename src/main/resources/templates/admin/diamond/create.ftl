<#include "/admin/header.ftl">

<h1>Create Diamond</h1>
<div style="width: 500px;">
    <form method="post" action="/admin/diamond/create">
        <div class="form-group">
            <label for="name">Name</label>
            <input type="text" class="form-control" id="name" placeholder="name">
        </div>
        <div class="form-group">
            <label for="name">Price</label>
            <input type="number" class="form-control" id="price" placeholder="price">
        </div>

        <#list DiamondType?values as dtype>
            <div class="radio">
                <label><input type="radio" name="optradio">${dtype}</label>
            </div>
        </#list>

        <div class="form-group">
            <label for="name">Carats</label>
            <input type="number" class="form-control" id="carats" placeholder="carats">
        </div>

        <div class="form-group">
            <label for="clarity">Clarity</label>
            <input type="number" class="form-control" id="clarity" placeholder="clarity">
        </div>

        <button type="submit" class="btn btn-default">Submit</button>
    </form>
</div>

<#include "/admin/footer.ftl">




