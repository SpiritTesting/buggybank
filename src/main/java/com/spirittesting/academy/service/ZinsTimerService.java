package com.spirittesting.academy.service;

import com.spirittesting.academy.domain.Euro;
import com.spirittesting.academy.domain.Kunde;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@EnableScheduling
@Service
public class ZinsTimerService {

    private static final Logger log = LoggerFactory.getLogger(ZinsTimerService.class);
    private static final String ZINSKONTO = "ZINS";
    private final KontoService kontoService;
    private final ZahlungsService zahlungsService;

    @Value("${buggybank.zinsen.zinssatz.haben:0.01}")
    private String guthabenZins = "0.01";
    @Value("${buggybank.zinsen.zinssatz.soll:-0.10}")
    private String sollZins = "-0.10";
    @Value("${buggybank.zinsen.delay.initial:1000}")
    private String initialDelay;
    @Value("${buggybank.zinsen.delay.rate:1000}")
    String delayRate;

    public ZinsTimerService(KontoService kontoService, ZahlungsService zahlungsService) {
        this.kontoService = kontoService;
        this.zahlungsService = zahlungsService;
        this.kontoService.addKonto(ZINSKONTO);
    }

    @PostConstruct
    public void initialize() {
        log.info("Aktueller Habenzinssatz: {}%", guthabenZins);
        log.info("Aktueller Sollzinssatz: {}%", sollZins);
        log.info("Zinsberechnung beginnt nach {}ms", initialDelay);
        log.info("Zinsberechnung danach alle {}ms", delayRate);
    }

    @Scheduled(
            initialDelayString = "${buggybank.zinsen.delay.initial:1000}",
            fixedRateString = "${buggybank.zinsen.delay.rate:1000}")
    void berechneZinsen() {
        // Guthabenzinsen
        kontoService.getAllKonten().stream().filter(konto -> konto.getBetrag().compareTo(new Euro()) > 0).forEach(konto -> zahlungsService.addZahlung(ZINSKONTO, konto.getKontonummer(), new Euro(konto.getBetrag().multiply(new BigDecimal(guthabenZins))), true));
        log.info("Guthabenzinsen verrechnet");
        // Sollzinsen
        kontoService.getAllKonten().stream().filter(konto -> konto.getBetrag().compareTo(new Euro()) < 0).forEach(konto -> zahlungsService.addZahlung(konto.getKontonummer(), ZINSKONTO, new Euro(konto.getBetrag().multiply(new BigDecimal(sollZins))), true));
        log.info("Sollzinsen verrechnet");
    }


}
