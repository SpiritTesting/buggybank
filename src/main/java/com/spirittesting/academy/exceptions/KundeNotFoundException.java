package com.spirittesting.academy.exceptions;

public class KundeNotFoundException extends Exception {

    public KundeNotFoundException(String kundennummer) {
        super("Kunde " + kundennummer + " nicht gefunden");
    }

}
