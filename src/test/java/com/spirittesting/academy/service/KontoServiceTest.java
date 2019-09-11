package com.spirittesting.academy.service;

import com.spirittesting.academy.domain.Euro;
import com.spirittesting.academy.domain.Konto;
import com.spirittesting.academy.domain.Kunde;
import com.spirittesting.academy.exceptions.KontoNotFoundException;
import com.spirittesting.academy.repository.KontoRepository;
import com.spirittesting.academy.repository.ZahlungRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class KontoServiceTest {

    KontoRepository kontoRepository;
    KontoService kontoService;
    Konto konto = new Konto("1", new Kunde("1", "Hannes"));

    @BeforeEach
    void init(@Mock KontoRepository kontoRepository, @Mock ZahlungRepository zahlungRepository) {
        kontoService = new KontoService(new KontonummerService(1, "%d"), kontoRepository, zahlungRepository);
        this.kontoRepository = kontoRepository;

        Mockito.lenient().when(kontoRepository.save(any())).thenReturn(konto);
        Mockito.lenient().when(kontoRepository.findById(eq("1"))).thenReturn(Optional.of(konto));
        Mockito.lenient().when(kontoRepository.findById(not(eq("1")))).thenReturn(Optional.empty());
        Mockito.lenient().when(kontoRepository.findAllByKunde_Kundennummer(eq("1"))).thenReturn(Collections.singleton(konto));
        Mockito.lenient().when(kontoRepository.findAllByKunde_Kundennummer(not(eq("1")))).thenReturn(Collections.emptySet());
    }

    @Test
    void addKonto() {
        Kunde kunde = new Kunde("1", "Hannes");
        Konto konto = kontoService.addKonto(kunde);
        assertEquals("1", konto.getKontonummer());
        assertEquals(kunde, konto.getKunde());
        assertEquals(new Euro(), konto.getKreditrahmen());
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

    @Test void setKreditrahmen() {
        Konto konto = kontoService.getKonto("1");
        konto.setKreditrahmen(new Euro(100));
        kontoService.setKreditrahmen("1", new Euro(100));

        Mockito.verify(kontoRepository).save(eq(konto));
    }


}
