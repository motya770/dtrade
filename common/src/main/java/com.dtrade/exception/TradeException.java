package com.dtrade.exception;

/**
 * Created by kudelin on 8/28/16.
 */
public class TradeException extends RuntimeException {

    public TradeException(String msg) {
        super(msg);
    }

    public TradeException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
