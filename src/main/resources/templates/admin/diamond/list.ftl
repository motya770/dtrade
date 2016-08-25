<#include "/admin/header.ftl">

    <h1>list6</h1>
    <div class="table-responsive">
        <table class="table">
            <tbody>
             <#if diamonds??>
                    <#list diamonds as diamond>
                        <tr>
                            <td>${diamond.id}</td>
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


