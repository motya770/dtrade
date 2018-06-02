package com.dtrade.repository.coinpayment;

import com.dtrade.model.account.Account;
import com.dtrade.model.coinpayment.CoinPayment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Repository
@Transactional
public interface CoinPaymentRepository extends JpaRepository<CoinPayment, Long> {

    Page<CoinPayment> findByAccount(Account account, Pageable pageable);

    @Deprecated
    @Query("select cp from CoinPayment as cp where cp.depositRequest.transactionId = :#{#transactionId} ")
    CoinPayment findByTransactionId(@Param("transactionId") String transactionId);

    @Query("select cp from CoinPayment as cp where cp.depositRequest.ipnId = :#{#ipnId} ")
    CoinPayment findDepositByIpnId(@Param("ipnId") String ipnId);

    //@Query("select cp from CoinPayment as cp where cp.outWithdrawRequest.ipnId = :#{#ipnId} ")
    //CoinPayment findOutWithdrawByIpnId(@Param("ipnId") String ipnId);

    @Query("select cp from CoinPayment as cp where cp.inWithdrawRequest.id = :#{#id} ")
    CoinPayment findInWithdrawById(@Param("id") String id);

    @Query("select cp from CoinPayment as cp where cp.coinPaymentStatus = 'CREATED' ")
    List<CoinPayment> getNotConfirmentCoinPayments();
}
