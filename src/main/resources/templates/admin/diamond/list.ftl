<#include "/admin/header.ftl">

<h1>Diamonds</h1>
<div class="table-responsive">
    <table class="table">
        <tbody>
        <#if diamonds??>
            <#list diamonds as diamond>
            <tr>
                <td> <a href="/admin/diamond/edit?id=${diamond.id}"> ${diamond.id}</a></td>
                <td>${diamond.name}</td>
                <td>${diamond.price}</td>
                <td>${diamond.diamondType}</td>
                <td>${diamond.carats}</td>
                <td>${diamond.clarity}</td>
            </tr>
            </#list>
        </#if>
        </tbody>
    </table>
</div>

<#include "/admin/footer.ftl">


