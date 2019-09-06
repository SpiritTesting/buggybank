package com.spirittesting.academy.exceptions;

public class KontoNotFoundException extends Exception {

    public KontoNotFoundException(String kontonummer) {
        super("Konto " + kontonummer + " nicht gefunden");
    }

}
