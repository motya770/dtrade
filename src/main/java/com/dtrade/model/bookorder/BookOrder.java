package com.dtrade.model.bookorder;

import com.dtrade.model.tradeorder.TradeOrder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kudelin on 7/10/17.
 */
@Data
public class BookOrder {

    private List<TradeOrder> buy;

    private List<TradeOrder> sell;

    public BookOrder() {
        buy = new ArrayList<>();
        sell = new ArrayList<>();
    }
}
