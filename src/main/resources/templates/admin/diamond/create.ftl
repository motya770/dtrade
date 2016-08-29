<#import "/spring.ftl" as spring/>

<#include "/admin/header.ftl">

<h1>Create Diamond</h1>
<div style="width: 500px;">
    <form method="post" action="/admin/diamond/create">
    <@spring.bind "diamond"/>

        <div class="form-group">
            <label for="name">Name</label>
            <input type="text" class="form-control" id="name" name="name" placeholder="name">
        </div>
        <div class="form-group">
            <label for="name">Price</label>
            <input type="number" class="form-control" name="price" id="price" placeholder="price">
        </div>

        <div class="radio">

        <@spring.formRadioButtons "diamond.diamondType", diamondTypes, ""/><br><br>

        </div>

        <div class="form-group">
            <label for="name">Carats</label>
            <input type="number" class="form-control" id="carats" name="carats" placeholder="carats">
        </div>

        <div class="form-group">
            <label for="clarity">Clarity</label>
            <input type="number" class="form-control" id="clarity" name="clarity" placeholder="clarity">
        </div>

        <button type="submit" class="btn btn-default">Submit</button>
    </form>
</div>

<#include "/admin/footer.ftl">




