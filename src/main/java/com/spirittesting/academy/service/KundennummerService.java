package com.spirittesting.academy.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class KundennummerService {

    private final AtomicInteger sequence;
    private final String pattern;

    public KundennummerService(@Value("${buggybank.kundennummern.initial:1}")int initialeKundennummer,
                               @Value("${buggybank.kundennummern.pattern:KDNR%04d}")String kundennummerPattern) {
        if (initialeKundennummer < 0 || kundennummerPattern == null) throw new IllegalArgumentException("Kundennummer oder Pattern fehlerhaft");
        this.pattern = kundennummerPattern;
        this.sequence = new AtomicInteger(initialeKundennummer);
    }

    public String next() {
        return String.format(pattern, sequence.getAndIncrement());
    }
}
