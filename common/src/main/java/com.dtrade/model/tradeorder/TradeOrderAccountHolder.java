package com.dtrade.model.tradeorder;

import com.dtrade.model.account.AccountDTO;
import lombok.Data;

@Data
public class TradeOrderAccountHolder {

    private TradeOrder tradeOrder;
    private AccountDTO accountDTO;
}
