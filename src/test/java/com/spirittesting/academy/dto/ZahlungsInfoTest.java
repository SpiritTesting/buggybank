package com.spirittesting.academy.dto;

import com.spirittesting.academy.domain.Konto;
import com.spirittesting.academy.domain.Kunde;
import com.spirittesting.academy.domain.Zahlung;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ZahlungsInfoTest {

    ZahlungsInfo zahlungsInfo = new ZahlungsInfo(new Zahlung(new Konto("1", new Kunde("1", "Hannes")), new Konto("2",
            new Kunde("2", "Werner")), BigDecimal.ONE));

    @Test
    void getDatum() {
        String expected = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        assertEquals(expected, zahlungsInfo.getDatum());
    }

    @Test
    void getQuelle() {
        assertEquals("1", zahlungsInfo.getQuelle());
    }

    @Test
    void getZiel() {
        assertEquals("2", zahlungsInfo.getZiel());
    }

    @Test
    void getBetrag() {
        assertEquals("1.00", zahlungsInfo.getBetrag());
    }

    @Test
    void compareTo() {
        ZahlungsInfo spaeter = new ZahlungsInfo(new Zahlung(new Konto(), new Konto(), BigDecimal.ZERO));
        assertEquals(0, zahlungsInfo.compareTo(spaeter));
    }
}
