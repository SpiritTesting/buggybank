package com.spirittesting.academy.service;

import com.spirittesting.academy.domain.Euro;
import com.spirittesting.academy.domain.Konto;
import com.spirittesting.academy.domain.Kunde;
import com.spirittesting.academy.exceptions.KontoNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KontoServiceTest {

    KontoService kontoService = new KontoService(new KontonummerService(1, "%d"));
    Kunde kunde = new Kunde("1", "Hannes");
    Konto konto = kontoService.addKonto(kunde);

    @Test
    void addKonto() {
        assertEquals("1", konto.getKontonummer());
        assertEquals(kunde, konto.getKunde());
        assertEquals(0, konto.getZahlungen().size());
        assertEquals(new Euro(), konto.getKreditrahmen());
        assertEquals(new Euro(), konto.getBetrag());
    }

    @Test
    void getKonto() {
        assertEquals(konto, kontoService.getKonto("1"));
    }

    @Test
    void getKontoNotPresent() {
        assertThrows(KontoNotFoundException.class, () -> kontoService.getKonto("2"));
    }

    @Test
    void getKonten() {
        assertEquals(1, kontoService.getKonten("1").size());
        assertEquals(konto, kontoService.getKonten("1").toArray()[0]);
    }

    @Test void getKontenUnknownKunde() {
        assertNotNull(kontoService.getKonten("2"));
        assertTrue(kontoService.getKonten("2").isEmpty());
    }
}
