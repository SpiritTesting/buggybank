package com.spirittesting.academy.exceptions;

public class KontoDeckungException extends RuntimeException {
    public KontoDeckungException(String quellKontonummer) {
        super("Kontodeckung ungenügend bei " + quellKontonummer);
    }
}
