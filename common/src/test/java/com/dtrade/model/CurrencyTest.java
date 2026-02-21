package com.dtrade.model;

import com.dtrade.model.currency.Currency;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class CurrencyTest {

    @Test
    public void usd_isBaseCurrency() {
        assertTrue(Currency.USD.isBaseCurrency());
    }

    @Test
    public void btc_isNotBaseCurrency() {
        assertFalse(Currency.BTC.isBaseCurrency());
    }

    @Test
    public void eth_isNotBaseCurrency() {
        assertFalse(Currency.ETH.isBaseCurrency());
    }

    @Test
    public void getName_returnsHumanReadableName() {
        assertEquals("US Dollar", Currency.USD.getName());
        assertEquals("Bitcoin", Currency.BTC.getName());
        assertEquals("Ethereum", Currency.ETH.getName());
        assertEquals("Apple", Currency.APPLE.getName());
        assertEquals("Tesla", Currency.TESLA.getName());
    }

    @Test
    public void toString_returnsName() {
        assertEquals("US Dollar", Currency.USD.toString());
        assertEquals("Bitcoin", Currency.BTC.toString());
    }

    @Test
    public void onlyUSD_isBaseCurrency() {
        List<Currency> baseCurrencies = Arrays.stream(Currency.values())
                .filter(Currency::isBaseCurrency)
                .collect(Collectors.toList());

        assertEquals(1, baseCurrencies.size());
        assertEquals(Currency.USD, baseCurrencies.get(0));
    }

    @Test
    public void allCurrencies_haveNonNullValues() {
        for (Currency currency : Currency.values()) {
            assertNotNull(currency.name());
        }
    }
}
