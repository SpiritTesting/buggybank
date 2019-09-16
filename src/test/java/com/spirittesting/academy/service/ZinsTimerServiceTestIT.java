package com.spirittesting.academy.service;

import com.spirittesting.academy.domain.Euro;
import com.spirittesting.academy.domain.Konto;
import com.spirittesting.academy.domain.Kunde;
import com.spirittesting.academy.domain.Zahlung;
import com.spirittesting.academy.exceptions.KontoDeckungException;
import com.spirittesting.academy.repository.ZahlungRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Rollback(false)
@EnableScheduling
class ZinsTimerServiceTestIT {

    private static final Logger log = LoggerFactory.getLogger(ZinsTimerServiceTestIT.class);

    @Autowired
    ZahlungRepository zahlungRepository;
    @Autowired
    ZahlungsService zahlungsService;
    @Autowired
    KontoService kontoService;
    @Autowired
    KundeService kundeService;

    String kontoHannes = "12340001";
    String kontoWerner = "12340002";

    @Test
    @Sql({"/Kunden.sql", "/Maximalzahlung.sql"})
    void addZahlung() {
        assertEquals(1, zahlungRepository.count());
        try {
            log.info("Dieser Test wartet jetzt 500ms auf die Zinsberechnung");
            Thread.sleep(2000);
            log.info("Test läuft weiter");
        } catch (InterruptedException e) {
            fail();
        }
        assertEquals(new Euro(202), kontoService.getBetrag(kontoWerner));
        assertEquals(new Euro(-220), kontoService.getBetrag(kontoHannes));

    }

}
