package com.dtrade.model.bookorder;

import com.dtrade.model.tradeorder.TradeOrder;
import lombok.Data;

import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by kudelin on 7/10/17.
 */
@Data
public class BookOrder {

    private ConcurrentSkipListSet<TradeOrder> buyOrders;

    private ConcurrentSkipListSet<TradeOrder> sellOrders;

    private static int comparatorSell(TradeOrder o1, TradeOrder o2){
        int response = o1.getPrice().compareTo(o2.getPrice());
        if(response==0){
            response = o1.getCreationDate().compareTo(o2.getCreationDate());
        }
        if(response==0){
            response = o1.getId().compareTo(o2.getId());
        }
        return response;
    }

    private static int comparatorBuy(TradeOrder o1, TradeOrder o2){
        int response = o1.getPrice().compareTo(o2.getPrice()) * (-1);
        if(response==0){
            response = o1.getCreationDate().compareTo(o2.getCreationDate());
        }
        if(response==0){
            response = o1.getId().compareTo(o2.getId());
        }
        return response;
    }

    private static int comparator(TradeOrder o1, TradeOrder o2){
        return o1.getId().compareTo(o2.getId());
    }

    public BookOrder() {
        buyOrders = new ConcurrentSkipListSet<>(BookOrder::comparatorBuy);
        sellOrders = new ConcurrentSkipListSet<>(BookOrder::comparatorSell);
    }
}
