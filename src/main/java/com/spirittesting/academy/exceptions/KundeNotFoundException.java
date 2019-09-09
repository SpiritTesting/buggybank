package com.spirittesting.academy.exceptions;

public class KundeNotFoundException extends RuntimeException {

    public KundeNotFoundException(String kundennummer) {
        super("Kunde " + kundennummer + " nicht gefunden");
    }

}
