package com.spirittesting.academy.dto;

import com.spirittesting.academy.domain.Konto;
import com.spirittesting.academy.domain.Kunde;
import com.spirittesting.academy.domain.Zahlung;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KontoInfoTest {

    @Test
    void getBetrag() {
        assertEquals("0.00", new KontoInfo(new Konto()).getBetrag());
    }

    @Test
    void getBetrag_RundetKorrektAb() {
        Konto konto = new Konto();
        konto.addZahlung(new Zahlung(new Konto(), konto, BigDecimal.valueOf(499, 3)));
        assertEquals("0.00", new KontoInfo(konto).getBetrag());
    }

    @Test
    void getBetrag_RundetKorrektAuf() {
        Konto konto = new Konto("1", new Kunde());
        konto.addZahlung(new Zahlung(new Konto("2", new Kunde()), konto, BigDecimal.valueOf(5, 3)));
        assertEquals("0.01", new KontoInfo(konto).getBetrag());
    }


    @Test
    void getZahlungen() {
        Konto konto = new Konto("1", new Kunde());
        konto.addZahlung(new Zahlung(konto, new Konto("2", new Kunde()), BigDecimal.ONE));
        konto.addZahlung(new Zahlung(konto, new Konto("3", new Kunde()), BigDecimal.ONE));
        konto.addZahlung(new Zahlung(new Konto("4", new Kunde()), konto, BigDecimal.ONE));

        assertEquals(3, new KontoInfo(konto).getZahlungen().size());
    }

}
