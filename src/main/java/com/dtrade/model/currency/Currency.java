package com.dtrade.model.currency;

public enum  Currency {
    USD(true), BTC(true), ETH(true), LTC(false);

    Currency(boolean baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    private final boolean baseCurrency;

    public boolean isBaseCurrency() {
        return baseCurrency;
    }
}
