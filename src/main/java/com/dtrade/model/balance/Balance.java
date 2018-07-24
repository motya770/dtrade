package com.dtrade.model.balance;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Entity
public class Balance {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private BigDecimal bitcoinAmount;

    @NotNull
    private BigDecimal usdAmount;

    @NotNull
    private BigDecimal etherAmount;

    @NotNull
    private BigDecimal bitcoinFrozen;

    @NotNull
    private BigDecimal usdFrozen;

    @NotNull
    private BigDecimal etherFrozen;


    @NotNull
    private BigDecimal bitcoinOpen;

    @NotNull
    private BigDecimal usdOpen;

    @NotNull
    private BigDecimal etherOpen;

    /*
    @Version
    private Long version;*/

    public BalanceDTO getDTO(){

        BalanceDTO dto = new BalanceDTO();
        dto.setBitcoinAmount(bitcoinAmount.subtract(bitcoinFrozen).subtract(bitcoinOpen));
        dto.setEtherAmount(etherAmount.subtract(etherFrozen).subtract(etherOpen));
        dto.setUsdAmount(usdAmount.subtract(usdFrozen).subtract(usdOpen));

        return dto;

    }

}
