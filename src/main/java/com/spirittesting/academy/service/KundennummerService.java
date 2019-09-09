package com.spirittesting.academy.service;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class KundennummerService {

    private final AtomicInteger sequence;
    private final String pattern;

    public KundennummerService(int initialeKundennummer, String kundennummerPattern) {
        if (initialeKundennummer < 0 || kundennummerPattern == null) throw new IllegalArgumentException("Kundennummer oder Pattern fehlerhaft");
        this.pattern = kundennummerPattern;
        this.sequence = new AtomicInteger(initialeKundennummer);
    }

    public String next() {
        return String.format(pattern, sequence.getAndIncrement());
    }
}
