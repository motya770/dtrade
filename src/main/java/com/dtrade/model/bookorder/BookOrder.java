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

    private int comporator(TradeOrder o1, TradeOrder o2){
        int response = o1.getPrice().compareTo(o2.getPrice());
        if(response==0){
            response = o1.getCreationDate().compareTo(o2.getCreationDate());
        }
        if(response==0){
            response = o1.getId().compareTo(o2.getId());
        }
        return response;
    }

    public BookOrder() {
        buy = new ConcurrentSkipListSet<>(this::comporator);
        sell = new ConcurrentSkipListSet<>(this::comporator);
    }
}
