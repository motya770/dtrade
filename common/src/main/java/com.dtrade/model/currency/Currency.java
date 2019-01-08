package com.dtrade.model.currency;

import com.fasterxml.jackson.annotation.JsonValue;

//do not edit names !!!
public enum  Currency {
    USD(true, "US Dollar"),
    BTC( "Bitcoin"),
    ETH( "Ethereum"),
    LTC("Litecoin"),
    BHC("Bitcoin Cash"),
    ETC("Ethereum Classic"),
    APPLE("Apple"),
    MICROSOFT("Microsoft"),
    FACEBOOK("Facebook"),
    GOOGLE("Google"),
    TESLA("Tesla"),
    AMAZON("Amazon"),
    INVESCO("Invesco"),
    NETFLIX("Netflix"),
    AMD("AMD"),
    NVIDIA("Nvidia"),
    INTEL("Intel"),
    BROADCOM("Broadcom"),
    CISCO("Cisco"),
    MICRON("Micron"),
    ALIBABA("Alibaba"),
    BRISTOL("Bristol"),
    BANK_OF_AMERICA("Bank of America"),
    JP_MORGAN("JPMorgan Chase"),
    VISA("Visa"),
    BOENG("Boeing"),
    CITY_GROUP("City Group"),
    UNITED_HELTH("UnitedHealth Group"),
    BERKSHIRE("Berkshire Hathaway"),
    AT_T("AT&T"),
    EXXON_MOBIL("Exxon Mobil"),
    JOHNSON_JOHNSON("Johnson Johnson"),
    WELLS_FARGO("Wells Fargo"),
    DISNEY("Disney"),
    PFIZER("Pfizer"),
    MERCK("Merck & Co"),
    GENERAL_ELECTRIC("General Electric"),
    VERIZON("Verizon"),
    SALESFORCE("Salesforce"),
    BARCLAYS("Barclays"),
    COCA_COLA("Coca Cola"),
    EASY_JET("Easy Jet");

    Currency(){
        this.baseCurrency = false;
    }

    Currency(boolean baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    Currency(boolean baseCurrency, String name) {
        this.baseCurrency = baseCurrency;
        this.name  = name;
    }

    Currency(String name) {
        this.name = name;
    }


    private String name;

    private boolean baseCurrency;

    @JsonValue
    public String getName(){
        return name;
    }

    public boolean isBaseCurrency() {
        return baseCurrency;
    }

    @Override
    public String toString() {
        return name;
    }
}
