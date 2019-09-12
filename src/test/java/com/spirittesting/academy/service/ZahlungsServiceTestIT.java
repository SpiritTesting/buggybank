package com.spirittesting.academy.service;

import com.spirittesting.academy.domain.Euro;
import com.spirittesting.academy.exceptions.KontoDeckungException;
import com.spirittesting.academy.repository.KundeRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql({"/Kunden.sql", "/Maximalzahlung.sql"})
@Rollback(false)
class ZahlungsServiceTestIT {

    @Autowired
    ZahlungsService zahlungsService;
    @Autowired
    KontoService kontoService;
    @Autowired
    KundeService kundeService;

    static String kontoHannes = "1";
    static String kontoWerner = "2";

    @Autowired
    KundeRepository kundeRepository;

    @PostConstruct
    public void initialize() {
        LoggerFactory.getLogger(getClass()).error("{} Kunden in Datenbank", kundeRepository.count());
    }

    @Test
    @Transactional
    void addZahlung_gesendet() {
        assertEquals(new Euro(-200), kontoService.getBetrag(kontoHannes));
    }

    @Test
    @Transactional
    void addZahlung_erhalten() {
        assertEquals(new Euro(200), kontoService.getBetrag(kontoWerner));
    }

    @Test
    @Transactional
    @Rollback
    void zahlungUeberziehtKonto() {
        assertThrows(KontoDeckungException.class, () -> zahlungsService.addZahlung(kontoHannes,
                kontoWerner, new Euro(0, 1)));
    }

}
