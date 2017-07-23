package com.dtrade.repository.stock;

import com.dtrade.model.account.Account;
import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.stock.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kudelin on 6/27/17.
 */
@Repository
public interface StockRepository  extends JpaRepository<Stock, Long>{

    Stock findByAccountAndDiamond(Account account, Diamond diamond);

    List<Stock> findByAccount(Account account);
}
