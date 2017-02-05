<#import "/spring.ftl" as spring/>

<#include "/admin/header.ftl">

<h1>Create Diamond</h1>
<div style="width: 500px;">
    <form method="post" action="/admin/diamond/save">
    <@spring.bind "diamond"/>

        <div>
            <input type="hidden" name="id" value="${diamond.id}">
        </div>

        <div class="form-group">
            <label for="name">Name</label>
            <input type="text" class="form-control" id="name" name="name" placeholder="name" value="${diamond.name}">
        </div>
        <div class="form-group">
            <label for="name">Price</label>
            <input type="number" class="form-control" name="price" id="price" placeholder="price" value="${diamond.price}">
        </div>

        <div class="radio">

        <#assign stringStatusValue = diamond.diamondType />
        <@spring.formRadioButtons "diamond.diamondType", diamondTypes, ""/><br><br>

        </div>

        <div class="form-group">
            <label for="name">Carats</label>
            <input type="number" class="form-control" id="carats" name="carats" placeholder="carats" value="${diamond.carats}">
        </div>

        <div class="form-group">
            <label for="clarity">Clarity</label>
            <input type="number" class="form-control" id="clarity" name="clarity" placeholder="clarity" value="${diamond.clarity}">
        </div>

        <button type="submit" class="btn btn-default">Submit</button>
    </form>
</div>

<#include "/admin/footer.ftl">


