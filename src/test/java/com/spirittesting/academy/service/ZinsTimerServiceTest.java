package com.spirittesting.academy.service;

import com.spirittesting.academy.domain.Euro;
import com.spirittesting.academy.domain.Konto;
import com.spirittesting.academy.domain.Kunde;
import com.spirittesting.academy.domain.Zahlung;
import com.spirittesting.academy.exceptions.KontoNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class ZinsTimerServiceTest {

    ZinsTimerService zinsTimerService;
    ZahlungsService zahlungsService;
    Konto haben;
    Konto soll;
    Konto leer;

    @BeforeEach
    void init(@Mock KontoService kontoService) {
        zahlungsService = new ZahlungsService(kontoService);
        zinsTimerService = new ZinsTimerService(kontoService, zahlungsService);
        haben = new Konto("1", new Kunde("1", "Hannes"));
        soll = new Konto("2", new Kunde("2", "Werner"));
        leer = new Konto("3", new Kunde("3", "Egon"));

        haben.addZahlung(new Zahlung(new Konto("3", new Kunde("0", "NOBODY")), haben, new Euro(100, 0)));
        soll.addZahlung(new Zahlung(soll, new Konto("3", new Kunde("0", "NOBODY")), new Euro(100, 0)));
        Mockito.lenient().when(kontoService.getKonto(eq("1"))).thenReturn(haben);
        Mockito.lenient().when(kontoService.getKonto(eq("2"))).thenReturn(soll);
        Mockito.lenient().when(kontoService.getKonto(eq("3"))).thenReturn(leer);
        Mockito.lenient().when(kontoService.getKonto(eq("ZINS"))).thenReturn(new Konto("0", new Kunde("0", "NOBODY")));
        Mockito.lenient().when(kontoService.getAllKonten()).thenReturn(new HashSet<>(Arrays.asList(haben, soll, leer)));
    }

    @Test
    void berechneHabenzinsen() {
        zinsTimerService.berechneZinsen();
        assertEquals(new Euro(101, 0), haben.getBetrag());
    }

    @Test
    void berechneSollzinsen() {
        zinsTimerService.berechneZinsen();
        assertEquals(new Euro(-110, 0), soll.getBetrag());
    }

    @Test
    void berechneZinsenLeeresKonto() {
        zinsTimerService.berechneZinsen();
        assertEquals(new Euro(), leer.getBetrag());
    }
}
