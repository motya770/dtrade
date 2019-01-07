package com.dtrade.model.currency;

//do not edit names !!!
public enum  Currency {
    USD(true), BTC(true), ETH(true),
    LTC, BHC, ETC,
    APPLE, MICROSOFT, FACEBOOK, GOOGLE, TESLA,
    AMAZON, INVESCO, NETFLIX, AMD, NVIDIA, INTEL, BROADCOM, CISCO,
    MICRON, ALIBABA, BRISTOL, BANK_OF_AMERICA, JP_MORGAN,
    VISA, BOENG, CITY_GROUP, UNITED_HELTH, BERKSHIRE, AT_T,
    EXXON_MOBIL, JOHNSON_JOHNSON,
    WELLS_FARGO, DISNEY, PFIZER, MERCK,
    GENERAL_ELECTRIC, VERIZON, SALESFORCE, BARCLAYS, COCA_COLA, EASY_JET;

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
