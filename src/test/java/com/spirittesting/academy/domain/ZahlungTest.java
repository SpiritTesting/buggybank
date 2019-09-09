package com.spirittesting.academy.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ZahlungTest {

    @Test
    void testZahlung_SetztDatum() {
        assertNotNull(new Zahlung(new Konto("1", null), new Konto("2", null), new Euro("1")).getDatum());
    }

    @Test
    void testZahlung_QuelleIstNull() {
        assertThrows(IllegalArgumentException.class, () -> new Zahlung(null, new Konto("1", null), new Euro("1")));
    }

    @Test
    void testZahlung_ZielIstNull() {
        assertThrows(IllegalArgumentException.class, () -> new Zahlung(new Konto("1", null), null, new Euro("1")));
    }

    @Test
    void testZahlung_BetragIstNull() {
        assertThrows(IllegalArgumentException.class, () -> new Zahlung(new Konto("1", null), new Konto("2", null),
                null));
    }

}
