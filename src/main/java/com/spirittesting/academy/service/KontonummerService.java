package com.spirittesting.academy.service;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class KontonummerService {

    private final AtomicInteger sequence;

    private final String pattern;

    public KontonummerService(int initialeKontonummer, String kontonummerPattern) {
        if (initialeKontonummer < 0 || kontonummerPattern == null)
            throw new IllegalArgumentException("Kontonummer oder Pattern fehlerhaft");
        this.pattern = kontonummerPattern;
        this.sequence = new AtomicInteger(initialeKontonummer);
    }

    public String next() {
        return String.format(pattern, sequence.getAndIncrement());
    }
}
