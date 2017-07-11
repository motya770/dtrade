package com.dtrade.model.bookorder;

import com.dtrade.model.tradeorder.TradeOrder;
import lombok.Data;

import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by kudelin on 7/10/17.
 */
@Data
public class BookOrder {

    private ConcurrentSkipListSet<TradeOrder> buy;

    private ConcurrentSkipListSet<TradeOrder> sell;

    public BookOrder() {
        buy = new ConcurrentSkipListSet<>(((o1, o2) -> {
            return o1.getPrice().compareTo(o2.getPrice());
        }));
        sell = new ConcurrentSkipListSet<>((o1, o2)->{
            return o1.getPrice().compareTo(o2.getPrice());
        });
    }
}
