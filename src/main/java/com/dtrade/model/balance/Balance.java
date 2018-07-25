package com.dtrade.model.balance;


import lombok.Data;

import javax.persistence.Column;
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
    @Column(precision=19, scale=8)
    private BigDecimal bitcoinAmount;

    @NotNull
    @Column(precision=19, scale=2)
    private BigDecimal usdAmount;

    @NotNull
    @Column(precision=19, scale=8)
    private BigDecimal etherAmount;

    @NotNull
    @Column(precision=19, scale=8)
    private BigDecimal bitcoinFrozen;

    @NotNull
    @Column(precision=19, scale=2)
    private BigDecimal usdFrozen;

    @NotNull
    @Column(precision=19, scale=8)
    private BigDecimal etherFrozen;

    @NotNull
    @Column(precision=19, scale=8)
    private BigDecimal bitcoinOpen;

    @NotNull
    @Column(precision=19, scale=2)
    private BigDecimal usdOpen;

    @NotNull
    @Column(precision=19, scale=8)
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
