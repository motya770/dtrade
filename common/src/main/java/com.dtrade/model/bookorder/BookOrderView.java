package com.dtrade.model.bookorder;

import com.dtrade.model.tradeorder.TradeOrderDTO;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonRootName(value = "bookOrder")
public class BookOrderView {

    private List<TradeOrderDTO> buyOrders;
    private List<TradeOrderDTO> sellOrders;

    public BookOrderView(){
        buyOrders = new ArrayList<>();
        sellOrders = new ArrayList<>();
    }

    public BookOrderView(List<TradeOrderDTO> buyOrders, List<TradeOrderDTO> sellOrders){
        this.buyOrders = buyOrders;
        this.sellOrders = sellOrders;
    }
}
