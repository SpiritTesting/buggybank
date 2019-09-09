package com.spirittesting.academy.dto;

import com.spirittesting.academy.domain.Euro;
import com.spirittesting.academy.domain.Konto;
import com.spirittesting.academy.domain.Kunde;
import com.spirittesting.academy.domain.Zahlung;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KontoInfoTest {

    @Test
    void getBetrag() {
        assertEquals("EUR 0.00", new KontoInfo(new Konto("1", null)).getBetrag());
    }

    @Test
    void getBetrag_RundetKorrektAb() {
        Konto konto = new Konto("1", null);
        konto.addZahlung(new Zahlung(new Konto("1", null), konto, new Euro(new BigDecimal("0.0049"))));
        assertEquals("EUR 0.00", new KontoInfo(konto).getBetrag());
    }

    @Test
    void getBetrag_RundetKorrektAuf() {
        Konto konto = new Konto("1", new Kunde());
        konto.addZahlung(new Zahlung(new Konto("2", new Kunde()), konto, new Euro(new BigDecimal("0.005"))));
        assertEquals("EUR 0.01", new KontoInfo(konto).getBetrag());
    }


    @Test
    void getZahlungen() {
        Konto konto = new Konto("1", new Kunde());
        konto.addZahlung(new Zahlung(konto, new Konto("2", new Kunde()), new Euro("1")));
        konto.addZahlung(new Zahlung(konto, new Konto("3", new Kunde()), new Euro("1")));
        konto.addZahlung(new Zahlung(new Konto("4", new Kunde()), konto, new Euro("1")));

        assertEquals(3, new KontoInfo(konto).getZahlungen().size());
    }

}
