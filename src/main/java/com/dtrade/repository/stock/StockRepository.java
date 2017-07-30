package com.dtrade.repository.stock;

import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.stock.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kudelin on 6/27/17.
 */
@Repository
public interface StockRepository  extends JpaRepository<Stock, Long>{

    Stock findByAccountAndDiamond(Account account, Diamond diamond);

    @Query("select s from Stock s where s.account.id =  :#{#account.id}")
    List<Stock> findByAccount(@Param("account") Account account);
}
