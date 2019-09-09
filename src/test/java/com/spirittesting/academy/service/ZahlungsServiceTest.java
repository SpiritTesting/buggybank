package com.spirittesting.academy.service;

import com.spirittesting.academy.domain.Euro;
import com.spirittesting.academy.domain.Konto;
import com.spirittesting.academy.domain.Kunde;
import com.spirittesting.academy.domain.Zahlung;
import com.spirittesting.academy.exceptions.KontoDeckungException;
import com.spirittesting.academy.exceptions.KontoNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class ZahlungsServiceTest {


    ZahlungsService zahlungsService;
    Konto quelle;
    Konto ziel;

    @BeforeEach
    void init(@Mock KontoService kontoService) {
        zahlungsService = new ZahlungsService(kontoService);
        quelle = new Konto("1", new Kunde("1", "Hannes"));
        ziel = new Konto("2", new Kunde("2", "Werner"));

        quelle.addZahlung(new Zahlung(new Konto("3", new Kunde("0", "NOBODY")), quelle, new Euro(100, 00)));
        Mockito.lenient().when(kontoService.getKonto(eq("1"))).thenReturn(quelle);
        Mockito.lenient().when(kontoService.getKonto(eq("2"))).thenReturn(ziel);
        Mockito.lenient().when(kontoService.getKonto(eq("3"))).thenThrow(new KontoNotFoundException("3"));
    }

    @Test
    void addZahlung() {
        Zahlung zahlung = zahlungsService.addZahlung("1", "2", new Euro(5, 0));
        assertEquals(quelle, zahlung.getQuelle());
        assertEquals(ziel, zahlung.getZiel());
        assertEquals("EUR 5.00", zahlung.getBetrag().toString());
        assertTrue(Math.abs(Instant.now().toEpochMilli() - zahlung.getDatum().toEpochMilli()) < 1000);

        assertTrue(quelle.getZahlungen().contains(zahlung));
        assertTrue(ziel.getZahlungen().contains(zahlung));
    }

    @Test
    void addZahlungQuelleNichtGefunden() {
        assertThrows(KontoNotFoundException.class, () -> zahlungsService.addZahlung("3", "2", new Euro(5, 0)));
    }

    @Test
    void addZahlungZielNichtGefunden() {
        assertThrows(KontoNotFoundException.class, () -> zahlungsService.addZahlung("1", "3", new Euro(5, 0)));
    }

    @Test
    void addZahlungDeckungUngenuegend() {
        assertThrows(KontoDeckungException.class, () -> zahlungsService.addZahlung("1", "2", new Euro(100, 1)));
    }

    @Test
    void addZahlungMitKreditrahmen() {
        quelle.setKreditrahmen(new Euro(100,0));
        zahlungsService.addZahlung("1", "2", new Euro(200, 0));
    }

    @Test
    void addZahlungMitKreditrahmenUndUngenuegenderDeckung() {
        quelle.setKreditrahmen(new Euro(100, 0));
        assertThrows(KontoDeckungException.class, () -> zahlungsService.addZahlung("1", "2", new Euro(200, 1)));
    }
}
