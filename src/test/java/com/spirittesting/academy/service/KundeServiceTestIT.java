package com.spirittesting.academy.service;

import com.spirittesting.academy.domain.Kunde;
import com.spirittesting.academy.repository.KundeRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Sql("/Kunden.sql")
class KundeServiceTestIT {
    private static final Logger log = LoggerFactory.getLogger(ZinsTimerServiceTestIT.class);

    @Autowired
    KundeService kundeService;
    @Autowired
    KundeRepository kundeRepository;

    @Test
    void addKunde() {
        final Kunde kunde = kundeService.addKunde("Günther");
        assertTrue(kundeRepository.existsById(kunde.getKundennummer()));
    }

    @Test
    void findByKundennummer() {
        final Kunde kunde = kundeService.findByKundennummer("2");
        assertEquals("Werner", kunde.getName());
    }

    @Test
    void findByName() {
        final Set<Kunde> kunden = kundeService.findByName("Werner");
        assertEquals(1, kunden.size());
    }
}
