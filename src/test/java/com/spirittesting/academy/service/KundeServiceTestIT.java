package com.spirittesting.academy.service;

import com.spirittesting.academy.domain.Kunde;
import com.spirittesting.academy.repository.KundeRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class KundeServiceTestIT {
    private static final Logger log = LoggerFactory.getLogger(ZinsTimerServiceTestIT.class);

    @Autowired
    KundeService kundeService;
    @Autowired
    KundeRepository kundeRepository;

    @Test
    void addKunde() {
        final Kunde hannes = kundeService.addKunde("Hannes");
        assertTrue(kundeRepository.existsById(hannes.getKundennummer()));
    }

    @Test
    void findByKundennummer() {
        kundeRepository.save(new Kunde("findByKundennummer", "findByKundennummer"));
        final Kunde kunde = kundeService.findByKundennummer("findByKundennummer");
        assertEquals("findByKundennummer", kunde.getName());
    }

    @Test
    void findByName() {
        kundeRepository.save(new Kunde("findByName", "findByName"));
        final Set<Kunde> kunden = kundeService.findByName("findByName");
        assertEquals(1, kunden.size());
    }
}
