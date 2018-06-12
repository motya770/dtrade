<#include "/admin/header.ftl">

<h1>Coin Payments</h1>
<div class="table-responsive">
    <table class="table">
        <tr>
            <th>Id</th>
            <th>Account</th>
            <th>CP Status</th>
            <th>CP Type</th>
            <th>Date</th>
        </tr>
        <tbody>
        <#if coinPayments??>
            <#list coinPayments.content as cp>
            <tr>
                <td>${cp.id}</td>
                <td><a href="/admin/account/show?id=${cp.account.id}"> ${cp.account.mail}</a></td>
                <td>${cp.coinPaymentStatus}</td>
                <td>${cp.coinPaymentType}</td>

                <td>
                <#if cp.creationDate??>
                    ${cp.creationDate?number_to_datetime}
                </#if>
                </td>

            </tr>
            </#list>

            <a href="/admin/coinpayment/list?pageNumber=${coinpayments.number + 1}">
               ${coinpayments.number + 1}
            </a>
        </#if>
        </tbody>
    </table>
</div>

<#include "/admin/footer.ftl">


