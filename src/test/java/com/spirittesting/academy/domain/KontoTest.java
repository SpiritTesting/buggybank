package com.spirittesting.academy.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class KontoTest {

    Konto konto = new Konto("1", new Kunde());
    Zahlung zahlung = new Zahlung(konto, new Konto(), BigDecimal.TEN);

    @Test
    void getZahlungen_IstNieNull() {
        assertNotNull(konto.getZahlungen());
    }

    @Test
    void getZahlungen_IstLeer() {
        assertTrue(konto.getZahlungen().isEmpty());
    }

    @Test
    void getZahlungen_IstUnveraenderbar() {
        assertThrows(UnsupportedOperationException.class, () -> konto.getZahlungen().add(zahlung));
    }

    @Test
    void addZahlung() {
        konto.addZahlung(zahlung);
        assertTrue(konto.getZahlungen().contains(zahlung));
        assertEquals(1, konto.getZahlungen().size());
    }

    @Test
    void addZahlung_IsNullSafe() {
        assertThrows(IllegalArgumentException.class, () -> konto.addZahlung(null));
    }

    @Test
    void removeZahlung() {
        // Vorbedingung
        Zahlung zahlung1 = new Zahlung(new Konto(), konto, BigDecimal.TEN);
        konto.addZahlung(zahlung1);
        konto.addZahlung(zahlung);

        // Test
        konto.removeZahlung(zahlung1);

        // Prüfung
        assertEquals(1, konto.getZahlungen().size());
        assertTrue(konto.getZahlungen().contains(zahlung));
    }

    @Test
    void removeZahlung_IsNullSafe() {
        assertThrows(IllegalArgumentException.class, () -> konto.removeZahlung(null));
    }

    @Test
    void getBetrag_OhneZahlungen() {
        assertEquals(BigDecimal.ZERO, konto.getBetrag());
    }

    @Test
    void getBetrag_MitZahlungen() {
        konto.addZahlung(zahlung);
        konto.addZahlung(new Zahlung(konto, new Konto(), BigDecimal.ONE));

        assertEquals(BigDecimal.valueOf(-11), konto.getBetrag());
    }
}
