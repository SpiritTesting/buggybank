package com.spirittesting.academy.service;

import com.spirittesting.academy.domain.Konto;
import com.spirittesting.academy.domain.Kunde;
import com.spirittesting.academy.repository.KontoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KontoServiceTestIT {

    @Autowired
    KontoService kontoService;
    @Autowired
    KontoRepository kontoRepository;
    @Autowired
    KundeService kundeService;

    @Test
    void addKonto() {
        final Kunde kunde = kundeService.addKunde("addKonto");
        final Konto konto = kontoService.addKonto(kunde);
        assertTrue(kontoRepository.findById(konto.getKontonummer()).isPresent());
    }

    @Test
    void testAddKonto() {
        final Konto konto = kontoService.addKonto("addKonto");
        assertTrue(kontoRepository.findById("addKonto").isPresent());
    }

    @Test
    void getKonto() {
        final Kunde kunde = kundeService.addKunde("getKontoKunde");
        kontoRepository.save(new Konto("getKonto", kunde));
        assertNotNull(kontoService.getKonto("getKonto"));
    }

    @Test
    void getKonten() {
        final Kunde kunde = kundeService.addKunde("getKontenKunde");
        kontoRepository.save(new Konto("getKonten1", kunde));
        kontoRepository.save(new Konto("getKonten2", kunde));
        kontoRepository.save(new Konto("getKonten3", kunde));
        assertEquals(3, kontoService.getKonten(kunde.getKundennummer()).size());
    }

    @Test
    void getAllKonten() {
        assertEquals(kontoRepository.count(), kontoService.getAllKonten().size());
    }
}
