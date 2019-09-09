package com.spirittesting.academy.service;

import com.spirittesting.academy.domain.Kunde;
import com.spirittesting.academy.exceptions.KundeNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class KundeServiceTest {

    KundeService kundeService = new KundeService(new KundennummerService(1, "%d"));
    Kunde kunde = kundeService.addKunde("Hannes");


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
