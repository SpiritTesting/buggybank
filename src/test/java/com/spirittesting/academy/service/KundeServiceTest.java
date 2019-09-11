package com.spirittesting.academy.service;

import com.spirittesting.academy.domain.Euro;
import com.spirittesting.academy.domain.Konto;
import com.spirittesting.academy.domain.Kunde;
import com.spirittesting.academy.domain.Zahlung;
import com.spirittesting.academy.exceptions.KontoNotFoundException;
import com.spirittesting.academy.exceptions.KundeNotFoundException;
import com.spirittesting.academy.repository.KundeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalMatchers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class KundeServiceTest {

    KundeService kundeService;
    Kunde kunde = new Kunde("1", "Hannes");

    @BeforeEach
    void init(@Mock KundeRepository kundeRepository) {
        kundeService = new KundeService(new KundennummerService(1, "%d"), kundeRepository);

        Mockito.lenient().when(kundeRepository.save(any())).thenReturn(kunde);
        Mockito.lenient().when(kundeRepository.findById(eq("1"))).thenReturn(Optional.of(kunde));
        Mockito.lenient().when(kundeRepository.findById(not(eq("1")))).thenReturn(Optional.empty());
        Mockito.lenient().when(kundeRepository.findAllByNameStartingWith(eq("Hannes"))).thenReturn(Collections.singleton(kunde));
        Mockito.lenient().when(kundeRepository.findAllByNameStartingWith(not(eq("Hannes")))).thenReturn(Collections.emptySet());
    }

    @Test
    void addKunde() {
        assertEquals("1", kunde.getKundennummer());
        assertEquals("Hannes", kunde.getName());
    }

    @Test
    void findByName() {
        assertEquals(Collections.singleton(kunde), kundeService.findByName("Hannes"));
    }

    @Test
    void findByNameNotPresent() {
        assertEquals(new HashSet(),
                kundeService.findByName("Werner"));
    }

    @Test
    void findByKundennummer() {
        assertEquals(kunde, kundeService.findByKundennummer("1"));
    }

    @Test
    void findByKundennummerNotPresent() {
        assertThrows(KundeNotFoundException.class, () -> kundeService.findByKundennummer("2"));
    }

}
