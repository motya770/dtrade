package com.dtrade.model.currency;

public enum  Currency {
    USD(true), BTC(true), ETH(true),
    LTC, BHC, ETC,
    APPLE, MICROSOFT, FACEBOOK, GOOGLE, TESLA;

    Currency(){
        this.baseCurrency = false;
    }

    Currency(boolean baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    private final boolean baseCurrency;

    public boolean isBaseCurrency() {
        return baseCurrency;
    }
}
