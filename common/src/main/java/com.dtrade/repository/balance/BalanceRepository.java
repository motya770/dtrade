package com.dtrade.repository.balance;

import com.dtrade.model.account.Account;
import com.dtrade.model.balance.Balance;
import com.dtrade.model.currency.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {

    @Query("select b from Balance b where b.account.id  = :#{#account.id} and b.currency = :#{#currency}")
    Balance getBalance(@Param("account") Account account, @Param("currency") Currency currency);

    @Query("select b from Balance b where b.account.id  = :#{#account.id} and b.currency in :currencies")
    List<Balance> getBaseBalancesByAccount(@Param("account") Account account, @Param("currencies") List<Currency> currencies);
}