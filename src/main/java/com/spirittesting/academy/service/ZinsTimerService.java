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
import javax.transaction.Transactional;
import java.math.BigDecimal;

@EnableScheduling
@Service
public class ZinsTimerService {

    private static final Logger log = LoggerFactory.getLogger(ZinsTimerService.class);

    @Value("${buggybank.zinsen.delay.initial:1000}")
    private String initialDelay;
    @Value("${buggybank.zinsen.delay.rate:1000}")
    private String delayRate;

    private ZinsService zinsService;

    public ZinsTimerService(ZinsService zinsService) {
        this.zinsService = zinsService;
    }

    @PostConstruct
    public void initialize() {
        log.info("Zinsberechnung beginnt nach {}ms", initialDelay);
        log.info("Zinsberechnung danach alle {}ms", delayRate);
    }

    @Scheduled(
            initialDelayString = "${buggybank.zinsen.delay.initial:1000}",
            fixedRateString = "${buggybank.zinsen.delay.rate:1000}")
    void starteZinsberechnung() {
        zinsService.berechneZinsen();
    }


}
