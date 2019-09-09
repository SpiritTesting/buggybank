package com.spirittesting.academy.exceptions;

public class ZahlungFehlgeschlagenException extends RuntimeException {

    public ZahlungFehlgeschlagenException(String reason) {
        super(reason);
    }

}
