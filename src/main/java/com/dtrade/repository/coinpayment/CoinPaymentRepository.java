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

@Repository
@Transactional
public interface CoinPaymentRepository extends JpaRepository<CoinPayment, Long> {

    Page<CoinPayment> findByAccount(Account account, Pageable pageable);

    @Query("select cp from CoinPayment as cp where cp.coinPaymentRequest.ipnId = :#{#ipnId} ")
    CoinPayment findByIpnId(@Param("ipnId") String ipnId);
}
