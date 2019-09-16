package com.spirittesting.academy.service;

import com.spirittesting.academy.domain.Euro;
import com.spirittesting.academy.domain.Konto;
import com.spirittesting.academy.domain.Kunde;
import com.spirittesting.academy.domain.Zahlung;
import com.spirittesting.academy.exceptions.KontoDeckungException;
import com.spirittesting.academy.exceptions.KontoNotFoundException;
import com.spirittesting.academy.repository.ZahlungRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class ZahlungsServiceTest {


    ZahlungsService zahlungsService;
    Konto quelle;
    Konto ziel;
    private ZahlungRepository zahlungRepository;

    @BeforeEach
    void init(@Mock KontoService kontoService, @Mock ZahlungRepository zahlungRepository) {
        this.zahlungRepository = zahlungRepository;
        zahlungsService = new ZahlungsService(kontoService, zahlungRepository);
        quelle = new Konto("12340001", new Kunde("1", "Hannes"));
        ziel = new Konto("12340002", new Kunde("2", "Werner"));

        Mockito.lenient().when(kontoService.getKonto(eq("12340001"))).thenReturn(quelle);
        Mockito.lenient().when(kontoService.getKonto(eq("12340002"))).thenReturn(ziel);
        Mockito.lenient().when(kontoService.getKonto(eq("12340003"))).thenThrow(new KontoNotFoundException("12340003"));
        Mockito.lenient().when(zahlungRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);
        Mockito.lenient().when(kontoService.getBetrag(any(Konto.class))).thenReturn(new Euro(100));
    }

    @Test
    void addZahlung() {
        Zahlung zahlung = zahlungsService.addZahlung("12340001", "12340002", new Euro(5, 0));
        assertEquals(quelle, zahlung.getQuelle());
        assertEquals(ziel, zahlung.getZiel());
        assertEquals("EUR 5.00", zahlung.getBetrag().toString());
        assertTrue(Math.abs(Instant.now().toEpochMilli() - zahlung.getDatum().toEpochMilli()) < 1000);
        Mockito.verify(zahlungRepository).save(eq(zahlung));
    }

    @Test
    void addZahlungQuelleNichtGefunden() {
        assertThrows(KontoNotFoundException.class, () -> zahlungsService.addZahlung("12340003", "12340002", new Euro(5, 0)));
    }

    @Test
    void addZahlungZielNichtGefunden() {
        assertThrows(KontoNotFoundException.class, () -> zahlungsService.addZahlung("12340001", "12340003", new Euro(5, 0)));
    }

    @Test
    void addZahlungDeckungUngenuegend() {
        assertThrows(KontoDeckungException.class, () -> zahlungsService.addZahlung("12340001", "12340002", new Euro(100, 1)));
    }

    @Test
    void addZahlungMitKreditrahmen() {
        quelle.setKreditrahmen(new Euro(100,0));
        zahlungsService.addZahlung("12340001", "12340002", new Euro(200, 0));
    }

    @Test
    void addZahlungMitKreditrahmenUndUngenuegenderDeckung() {
        quelle.setKreditrahmen(new Euro(100, 0));
        assertThrows(KontoDeckungException.class, () -> zahlungsService.addZahlung("12340001", "12340002", new Euro(200, 1)));
    }
}
