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
    @Value("${buggybank.zinsen.delay.rate:1000}")
    private long delayRate;

    @Test
    void addZahlung() {
        final Kunde hannes = kundeService.addKunde("Hannes");
        final Kunde werner = kundeService.addKunde("Werner");

        final Konto kontoHannes = kontoService.addKonto(hannes);
        final Konto kontoWerner = kontoService.addKonto(werner);

        kontoHannes.setKreditrahmen(new Euro(200));

        final Zahlung zahlung = zahlungsService.addZahlung(kontoHannes.getKontonummer(), kontoWerner.getKontonummer(), new Euro(200));

        try {
            log.info("Dieser Test wartet jetzt {}ms auf die Zinsberechnung", initialDelay + delayRate / 2);
            Thread.sleep(initialDelay + delayRate / 2);
        } catch (InterruptedException e) {
            fail();
        }

        assertEquals(new Euro(202), kontoWerner.getBetrag());
        assertEquals(new Euro(-220), kontoHannes.getBetrag());

    }

}
