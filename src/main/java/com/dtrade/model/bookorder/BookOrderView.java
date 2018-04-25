package com.dtrade.model.bookorder;

import com.dtrade.model.tradeorder.TradeOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import java.util.List;

@Data
@JsonRootName(value = "bookOrder")
public class BookOrderView {

    private List<TradeOrder> buyOrders;
    private List<TradeOrder> sellOrders;

    public BookOrderView(List<TradeOrder> buyOrders, List<TradeOrder> sellOrders){
        this.buyOrders = buyOrders;
        this.sellOrders = sellOrders;
    }
}
