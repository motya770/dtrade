package com.dtrade.exception;

public class NotEnoughMoney extends  TradeException {

    public  NotEnoughMoney(){
        super("Not enough money for this operation.");
    }
    
    public  NotEnoughMoney(String str){
        super(str);
    }
}
