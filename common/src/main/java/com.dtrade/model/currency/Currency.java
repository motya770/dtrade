package com.dtrade.model.currency;

public enum  Currency {
    USDT(true), BTC(true), ETH(true), LTC(false), BHC(false), ETC(false);

    Currency(boolean baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    private final boolean baseCurrency;

    public boolean isBaseCurrency() {
        return baseCurrency;
    }
}
