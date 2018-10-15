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
            <th>Currency USD</th>
            <th>Amount USD</th>
            <th>Currency Coin</th>
            <th>Amount Coin</th>
            <th>Transaction Id</th>
            <th>Ipn Id</th>
            <th>External Id</th>
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

                <#if cp.depositRequest??>
                 <td>${cp.depositRequest.currencyUsd!""}</td>
                 <td>${cp.depositRequest.amountUsd!""}</td>
                 <td>${cp.depositRequest.currencyCoin!""} </td>
                 <td>${cp.depositRequest.amountCoin!""}</td>
                 <td>${cp.depositRequest.transactionId!""}</td>
                 <td>${cp.depositRequest.ipnId!""}</td>
                 <td></td>
                </#if>

                <#if cp.inWithdrawRequest??>
                  <td>${cp.inWithdrawRequest.currencyUsd!""}</td>
                  <td>${cp.inWithdrawRequest.amountUsd!""}</td>
                  <td>${cp.inWithdrawRequest.currencyCoin!""} </td>
                  <td>${cp.inWithdrawRequest.amountCoin!""}</td>
                  <td>${cp.inWithdrawRequest.transactionId!""}</td>
                  <td>${cp.inWithdrawRequest.ipnId!""}</td>
                  <td>${cp.inWithdrawRequest.id!""}</td>
                </#if>


            </tr>
            </#list>

            <a href="/admin/coinpayment/list?pageNumber=${coinPayments.number + 1}">
               ${coinPayments.number + 1}
            </a>
        </#if>
        </tbody>
    </table>
</div>

<#include "/admin/footer.ftl">


