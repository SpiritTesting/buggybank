package com.spirittesting.academy.service;

import com.spirittesting.academy.domain.Euro;
import com.spirittesting.academy.domain.Konto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class ZinsService {

    private static final Logger log = LoggerFactory.getLogger(ZinsService.class);
    private static final String ZINSKONTO = "000";
    private final KontoService kontoService;
    private final ZahlungsService zahlungsService;

    @Value("${buggybank.zinsen.zinssatz.haben:0.01}")
    private String guthabenZinsString = "0.01";
    @Value("${buggybank.zinsen.zinssatz.soll:-0.10}")
    private String sollZinsString = "-0.10";

    private BigDecimal sollZins;
    private BigDecimal guthabenZins;

    public ZinsService(KontoService kontoService, ZahlungsService zahlungsService) {
        this.kontoService = kontoService;
        this.zahlungsService = zahlungsService;
    }

    @PostConstruct
    public void initialize() {
        log.info("Aktueller Habenzinssatz: {}%", guthabenZinsString);
        log.info("Aktueller Sollzinssatz: {}%", sollZinsString);
        sollZins = new BigDecimal(sollZinsString);
        guthabenZins = new BigDecimal(guthabenZinsString);
    }

    @Transactional
    @Async
    public void berechneZinsen() {
        for (Konto konto : kontoService.getAllKonten()) {
            // auf dem Zinskonto selbst werden keine Zinsen berechnet
            if (konto.getKontonummer().equals(ZINSKONTO)) continue;

            Euro saldo = kontoService.getBetrag(konto);
            if (saldo.equals(Euro.ZERO)) continue;
            Euro zinsBetrag = zinsBetrag(saldo);
            String quelle = saldo.isNegative() ? konto.getKontonummer() : ZINSKONTO;
            String ziel = saldo.isNegative() ? ZINSKONTO : konto.getKontonummer();

            zahlungsService.addZahlung(quelle, ziel, zinsBetrag.absolute(), true);
        }
        log.info("Zinsen verrechnet");
    }

    private Euro zinsBetrag(Euro saldo) {
        return saldo.isNegative() ? saldo.multiply(sollZins) : saldo.multiply(guthabenZins);
    }

    /**
     * for testing
     */
    void setSollZins(BigDecimal sollZins) {
        this.sollZins = sollZins;
    }

    /**
     * for testing
     */
    void setGuthabenZins(BigDecimal guthabenZins) {
        this.guthabenZins = guthabenZins;
    }

}
