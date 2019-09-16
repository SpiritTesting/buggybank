package com.spirittesting.academy.service;

import com.spirittesting.academy.domain.Konto;
import com.spirittesting.academy.domain.Kunde;
import com.spirittesting.academy.repository.KontoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql({"/Kunden.sql"})
class KontoServiceTestIT {

    @Autowired
    KontoService kontoService;
    @Autowired
    KontoRepository kontoRepository;
    @Autowired
    KundeService kundeService;

    @Test
    void addKonto() {
        final Kunde kunde = kundeService.findByKundennummer("1");
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
        assertNotNull(kontoService.getKonto("12340002"));
    }

    @Test
    void getKonten() {
        final Kunde kunde = kundeService.addKunde("Egon");
        kontoRepository.save(new Konto("6", kunde));
        kontoRepository.save(new Konto("7", kunde));
        kontoRepository.save(new Konto("8", kunde));
        assertEquals(3, kontoService.getKonten(kunde.getKundennummer()).size());
    }

    @Test
    void getAllKonten() {
        assertEquals(kontoRepository.count(), kontoService.getAllKonten().size());
    }
}
