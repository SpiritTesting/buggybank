package com.spirittesting.academy.service;

import com.spirittesting.academy.domain.Euro;
import com.spirittesting.academy.domain.Konto;
import com.spirittesting.academy.domain.Kunde;
import com.spirittesting.academy.domain.Zahlung;
import com.spirittesting.academy.exceptions.KontoNotFoundException;
import com.spirittesting.academy.repository.ZahlungRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class ZinsServiceTest {

    ZinsService zinsService;
    ZahlungsService zahlungsService;
    Konto haben;
    Konto soll;
    Konto leer;

    @BeforeEach
    void init(@Mock KontoService kontoService, @Mock ZahlungsService zahlungsService) {
        this.zahlungsService = zahlungsService;
        zinsService = new ZinsService(kontoService, zahlungsService);
        haben = new Konto("1", new Kunde("1", "Hannes"));
        soll = new Konto("2", new Kunde("2", "Werner"));
        leer = new Konto("3", new Kunde("3", "Egon"));

        zinsService.setSollZins(new BigDecimal("0.1"));
        zinsService.setGuthabenZins(new BigDecimal("0.01"));

        Mockito.lenient().when(kontoService.getKonto(eq("1"))).thenReturn(haben);
        Mockito.lenient().when(kontoService.getKonto(eq("2"))).thenReturn(soll);
        Mockito.lenient().when(kontoService.getKonto(eq("3"))).thenReturn(leer);
        Mockito.lenient().when(kontoService.getKonto(eq("000"))).thenReturn(new Konto("0", new Kunde("0", "NOBODY")));
        Mockito.lenient().when(kontoService.getAllKonten()).thenReturn(new HashSet<>(Arrays.asList(haben, soll, leer)));

        Mockito.lenient().when(kontoService.getBetrag(eq(haben))).thenReturn(new Euro(100));
        Mockito.lenient().when(kontoService.getBetrag(eq(soll))).thenReturn(new Euro(-100));
        Mockito.lenient().when(kontoService.getBetrag(eq(leer))).thenReturn(Euro.ZERO);
    }

    @Test
    void berechneHabenzinsen() {
        zinsService.berechneZinsen();
        Mockito.verify(zahlungsService).addZahlung("000", haben.getKontonummer(), new Euro(1), "Zinsen auf Guthaben",
          true);
    }

    @Test
    void berechneSollzinsen() {
        zinsService.berechneZinsen();
        Mockito.verify(zahlungsService).addZahlung(soll.getKontonummer(), "000", new Euro(10), "Sollzinsen für in Anspruch genommenen Kredit", true);
    }

    @Test
    void berechneZinsenLeeresKonto() {
        zinsService.berechneZinsen();
        Mockito.verify(zahlungsService, Mockito.never()).addZahlung(eq(leer.getKontonummer()), any(), any(Euro.class),
                eq(true));
        Mockito.verify(zahlungsService, Mockito.never()).addZahlung(any(), eq(leer.getKontonummer()),
                any(Euro.class),
                eq(true));
    }
}
