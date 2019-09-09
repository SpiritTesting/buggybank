package com.spirittesting.academy.exceptions;

public class KontoNotFoundException extends RuntimeException {

    public KontoNotFoundException(String kontonummer) {
        super("Konto " + kontonummer + " nicht gefunden");
    }

}
