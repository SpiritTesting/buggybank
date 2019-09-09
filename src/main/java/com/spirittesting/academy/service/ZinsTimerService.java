package com.spirittesting.academy.service;

import com.spirittesting.academy.domain.Euro;
import com.spirittesting.academy.domain.Kunde;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigDecimal;

@EnableScheduling
public class ZinsTimerService {

    private static final Logger log = LoggerFactory.getLogger(ZinsTimerService.class);
    private static final String ZINSKONTO = "ZINS";
    private static final BigDecimal HABENZINS = BigDecimal.valueOf(1, 2);
    private static final BigDecimal SOLLZINS = BigDecimal.valueOf(10, 2).multiply(BigDecimal.valueOf(-1));
    private final KontoService kontoService;
    private final ZahlungsService zahlungsService;

    public ZinsTimerService(KontoService kontoService, ZahlungsService zahlungsService) {
        this.kontoService = kontoService;
        this.zahlungsService = zahlungsService;
        this.kontoService.addKonto(new Kunde(ZINSKONTO, "BuggyBank"));
    }

    @Scheduled(initialDelay = 1000, fixedRate = 1000)
    void berechneZinsen() {
        // Guthabenzinsen
        kontoService.getAllKonten().stream().filter(konto -> konto.getBetrag().compareTo(new Euro()) > 0).forEach(konto -> zahlungsService.addZahlung(ZINSKONTO, konto.getKontonummer(), new Euro(konto.getBetrag().multiply(HABENZINS)), true));
        log.info("Guthabenzinsen verrechnet");
        // Sollzinsen
        kontoService.getAllKonten().stream().filter(konto -> konto.getBetrag().compareTo(new Euro()) < 0).forEach(konto -> zahlungsService.addZahlung(konto.getKontonummer(), ZINSKONTO, new Euro(konto.getBetrag().multiply(SOLLZINS)), true));
        log.info("Sollzinsen verrechnet");
    }


}
