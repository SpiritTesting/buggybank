package com.spirittesting.academy.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ZahlungTest {

    @Test
    void testZahlung_SetztDatum() {
        assertNotNull(new Zahlung(new Konto(), new Konto(), BigDecimal.ONE).getDatum());
    }

    @Test
    void testZahlung_QuelleIstNull() {
        assertThrows(IllegalArgumentException.class, () -> new Zahlung(null, new Konto(), BigDecimal.ONE));
    }

    @Test
    void testZahlung_ZielIstNull() {
        assertThrows(IllegalArgumentException.class, () -> new Zahlung(new Konto(), null, BigDecimal.ONE));
    }

    @Test
    void testZahlung_BetragIstNull() {
        assertThrows(IllegalArgumentException.class, () -> new Zahlung(new Konto(), new Konto(), null));
    }

}
