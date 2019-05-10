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
    BABA("Alibaba"),
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
    EASY_JET("Easy Jet"),
    ATVI("Blizzard"),
    ALXN("Alexion Pharmaceuticals "),
    ALGN	( "Align Technology"),
    AMGN("Amgen "),
    ADI	("Analog Devices"),
    AMAT	("Applied Materials "),
    AMS	 ("Holding N.V."),
    ADSK	 ("Autodesk "),
    ADP	 ("Automatic Data Processing "),
    BIDU	 ("Baidu "),
    BIIB	("Biogen "),
    BMRN	 ("BioMarin Pharmaceutical "),
    BKNG	 ("Booking Holdings "),
    AVGO	 ("Broadcom "),
    CDNS	 ("Cadence Design Systems "),
    CELG	 ("Celgene Corporation"),
    CERN	 ("Cerner Corporation"),
    CHTR	 ("Charter Communications"),
    CHKP	 ("Check Point"),
    CTAS	 ("Cintas Corporation"),
    CTXS	 ("Citrix Systems "),
    CTSH	 ("Cognizant"),
    CMCSA	 ("Comcast"),
    COST	 ("Costco"),
    CSX	 ("CSX Corporation"),
    CTRP	 ("Ctrip.com"),
    DLTR	 ("Dollar Tree "),
    EBAY	 ("eBay "),
    EA	 ("Electronic Arts "),
    EXPE	 ("Expedia Group "),
    FAST	 ("Fastenal Company"),
    FISV	 ("Fiserv "),
    GILD	 ("Gilead Sciences "),
    HAS	 ("Hasbro "),
    HSIC	 ("Henry Schein "),
    IDXX	 ("IDEXX Laboratories "),
    ILMN	 ("Illumina "),
    INCY	 ("Incyte Corporation"),
    INTU	 ("Intuit "),
    ISRG	 ("Intuitive Surgical "),
    JBHT	 ("J.B. Hunt Transport Services "),
    JD	 ("JD.com "),
    KLAC	 ("KLA-Tencor"),
    LRCX	 ("Lam Research"),
    LBTYA	 ("Liberty Global plc"),
    LBTYK	 ("Liberty Global plc"),
    LULU	 ("Lululemon Athletica"),
    MAR	 ("Marriott International"),
    MXIM	 ("Maxim Integrated Products"),
    MELI	 ("MercadoLibre "),
    MCHP	 ("Microchip Technology"),
    MU	 ("Micron Technology "),
    MDLZ	 ("Mondelez International "),
    MNST	("Monster Beverage"),
    MYL	 ("Mylan N.V."),
    NTAP	(" NetApp "),
    NTES	 ("NetEase "),
    NXPI	 ("NXP Semiconductors"),
    ORLY	 ("O'Reilly Automotive"),
    PCAR	 ("PACCAR "),
    PAYX	 ("Paychex "),
    PYPL	 ("PayPal Holdings "),
    PEP	 ("Pepsico "),
    REGN	 ("Regeneron Pharmaceuticals "),
    ROST	 ("Ross Stores "),
    SIRI	 ("Sirius XM Holdings "),
    SWKS	 ("Skyworks Solutions "),
    SBUX	 ("Starbucks Corporation"),
    SYMC	 ("Symantec Corporation"),
    SNPS	(" Synopsys "),
    TMUS	 ("T-Mobile US "),
    TTWO	 ("Take-Two Interactive Software "),
    TXN	 ("Texas Instruments"),
    KHC	 ("Kraft Heinz"),
    FOX	 ("21st Century Fox"),
    ULTA	 ("Ulta Beauty "),
    UAL	 ("United Continental Holdings "),
    VRSN	 ("VeriSign "),
    VRSK	("Verisk Analytics "),
    VRTX	 ("Vertex Pharmaceuticals"),
    WBA	 ("Walgreens Boots Alliance "),
    WDC	 ("Western Digital"),
    WLTW	 ("Willis Towers Watson"),
    WDAY	 ("Workday "),
    WYNN	 ("Wynn Resorts"),
    XEL	 ("Xcel Energy "),
    XLNX	 ("Xilinx ");
    
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
