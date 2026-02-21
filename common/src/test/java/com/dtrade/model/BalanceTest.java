package com.dtrade.model;

import com.dtrade.model.balance.Balance;
import com.dtrade.model.balance.BalanceDTO;
import com.dtrade.model.currency.Currency;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class BalanceTest {

    @Test
    public void getActualBalance_subtractsFrozenAndOpen() {
        Balance balance = new Balance();
        balance.setAmount(new BigDecimal("1000.00"));
        balance.setFrozen(new BigDecimal("200.00"));
        balance.setOpen(new BigDecimal("300.00"));

        BigDecimal actual = balance.getActualBalance();

        assertEquals(new BigDecimal("500.00"), actual);
    }

    @Test
    public void getActualBalance_zeroFrozenAndOpen() {
        Balance balance = new Balance();
        balance.setAmount(new BigDecimal("1000.00"));
        balance.setFrozen(BigDecimal.ZERO);
        balance.setOpen(BigDecimal.ZERO);

        BigDecimal actual = balance.getActualBalance();

        assertEquals(new BigDecimal("1000.00"), actual);
    }

    @Test
    public void getActualBalance_canBeNegative() {
        Balance balance = new Balance();
        balance.setAmount(new BigDecimal("100.00"));
        balance.setFrozen(new BigDecimal("200.00"));
        balance.setOpen(new BigDecimal("300.00"));

        BigDecimal actual = balance.getActualBalance();

        assertTrue(actual.compareTo(BigDecimal.ZERO) < 0);
        assertEquals(new BigDecimal("-400.00"), actual);
    }

    @Test
    public void getDTO_returnsActualBalance() {
        Balance balance = new Balance();
        balance.setAmount(new BigDecimal("5000.00"));
        balance.setFrozen(new BigDecimal("1000.00"));
        balance.setOpen(new BigDecimal("500.00"));

        BalanceDTO dto = balance.getDTO();

        assertNotNull(dto);
        assertEquals(new BigDecimal("3500.00"), dto.getBalance());
    }
}
