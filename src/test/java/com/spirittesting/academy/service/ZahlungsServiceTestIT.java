package com.spirittesting.academy.service;

import com.spirittesting.academy.domain.Euro;
import com.spirittesting.academy.domain.Konto;
import com.spirittesting.academy.domain.Kunde;
import com.spirittesting.academy.domain.Zahlung;
import com.spirittesting.academy.exceptions.KontoDeckungException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Rollback(false)
class ZahlungsServiceTestIT {

    @Autowired
    ZahlungsService zahlungsService;
    @Autowired
    KontoService kontoService;
    @Autowired
    KundeService kundeService;

    static String kontoHannes;
    static String kontoWerner;

    @Test
    @Transactional
    @Order(1)
    void vorbereitung() {
        final Kunde hannes = kundeService.addKunde("Hannes");
        final Kunde werner = kundeService.addKunde("Werner");

        kontoHannes = kontoService.addKonto(hannes).getKontonummer();
        kontoWerner = kontoService.addKonto(werner).getKontonummer();

        kontoService.setKreditrahmen(kontoHannes, new Euro(200));
        zahlungsService.addZahlung(kontoHannes, kontoWerner, new Euro(200));
        assertFalse(kontoHannes.isEmpty());
        assertFalse(kontoWerner.isEmpty());
    }

    @Test
    @Transactional
    @Order(2)
    void addZahlung_gesendet() {
        assertEquals(new Euro(-200), kontoService.getBetrag(kontoHannes));
    }

    @Test
    @Transactional
    @Order(3)
    void addZahlung_erhalten() {
        assertEquals(new Euro(200), kontoService.getBetrag(kontoWerner));
    }

    @Test
    @Transactional
    @Order(4)
    @Rollback(true)
    void zahlungUeberziehtKonto() {
        assertThrows(KontoDeckungException.class, () -> zahlungsService.addZahlung(kontoHannes,
                kontoWerner, new Euro(0, 1)));
    }

}
