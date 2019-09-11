package com.spirittesting.academy.service;

import com.spirittesting.academy.domain.Euro;
import com.spirittesting.academy.domain.Konto;
import com.spirittesting.academy.domain.Kunde;
import com.spirittesting.academy.domain.Zahlung;
import com.spirittesting.academy.exceptions.KontoDeckungException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ZinsTimerServiceTestIT {

    private static final Logger log = LoggerFactory.getLogger(ZinsTimerServiceTestIT.class);

    @Autowired
    ZahlungsService zahlungsService;
    @Autowired
    KontoService kontoService;
    @Autowired
    KundeService kundeService;

    @Value("${buggybank.zinsen.delay.initial:1000}")
    private long initialDelay;

    @Test
    void addZahlung() {
        final Kunde hannes = kundeService.addKunde("Hannes");
        final Kunde werner = kundeService.addKunde("Werner");

        final Konto kontoHannes = kontoService.addKonto(hannes);
        final Konto kontoWerner = kontoService.addKonto(werner);

        kontoService.setKreditrahmen(kontoHannes.getKontonummer(), new Euro(200));

        final Zahlung zahlung = zahlungsService.addZahlung(kontoHannes.getKontonummer(), kontoWerner.getKontonummer(), new Euro(200));

        try {
            log.info("Dieser Test wartet jetzt {}ms auf die Zinsberechnung", initialDelay);
            Thread.sleep(initialDelay);
            log.info("Test läuft weiter");
        } catch (InterruptedException e) {
            fail();
        }

        assertEquals(new Euro(202), kontoService.getBetrag(kontoWerner));
        assertEquals(new Euro(-220), kontoService.getBetrag(kontoHannes));

    }

}
