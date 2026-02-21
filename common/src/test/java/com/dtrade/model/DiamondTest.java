package com.dtrade.model;

import com.dtrade.model.diamond.Diamond;
import com.dtrade.model.diamond.DiamondStatus;
import com.dtrade.model.currency.Currency;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class DiamondTest {

    @Test
    public void equals_sameId_returnsTrue() {
        Diamond d1 = new Diamond();
        d1.setId(1L);

        Diamond d2 = new Diamond();
        d2.setId(1L);

        assertEquals(d1, d2);
    }

    @Test
    public void equals_differentId_returnsFalse() {
        Diamond d1 = new Diamond();
        d1.setId(1L);

        Diamond d2 = new Diamond();
        d2.setId(2L);

        assertNotEquals(d1, d2);
    }

    @Test
    public void equals_sameInstance_returnsTrue() {
        Diamond d = new Diamond();
        d.setId(1L);

        assertEquals(d, d);
    }

    @Test
    public void equals_differentType_returnsFalse() {
        Diamond d = new Diamond();
        d.setId(1L);

        assertNotEquals(d, "not a diamond");
    }

    @Test
    public void hashCode_sameId_sameHash() {
        Diamond d1 = new Diamond();
        d1.setId(5L);

        Diamond d2 = new Diamond();
        d2.setId(5L);

        assertEquals(d1.hashCode(), d2.hashCode());
    }

    @Test
    public void toString_containsId() {
        Diamond d = new Diamond();
        d.setId(99L);

        assertTrue(d.toString().contains("99"));
    }

    @Test
    public void diamondStatus_values() {
        DiamondStatus[] statuses = DiamondStatus.values();
        assertEquals(7, statuses.length);
        assertEquals(DiamondStatus.CREATED, DiamondStatus.valueOf("CREATED"));
        assertEquals(DiamondStatus.ENLISTED, DiamondStatus.valueOf("ENLISTED"));
        assertEquals(DiamondStatus.ROBO_HIDDEN, DiamondStatus.valueOf("ROBO_HIDDEN"));
    }
}
