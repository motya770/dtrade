package com.dtrade.model.balanceactivity;

import com.dtrade.model.account.Account;
import com.dtrade.model.tradeorder.TradeOrder;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class BalanceActivityCreator {

   private Account buyer;
   private Account seller;
   private TradeOrder buyOrder;
   private TradeOrder sellOrder;
   private BigDecimal realAmount;
   private BigDecimal price;
}
