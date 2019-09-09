package com.spirittesting.academy.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Euro extends BigDecimal {

    public Euro() {
        super(0);
    }

    public Euro(String value) {
        super(value.startsWith("EUR ") ? value.substring(4).toCharArray() : value.toCharArray());
    }

    public Euro(int euro, int cent) {
        super(Integer.toString(euro) + "." + Integer.toString(cent));
    }

    public Euro(BigDecimal value) {
        super(value.toString());
    }

    @Override
    public String toString() {
        return "EUR " + super.setScale(2, RoundingMode.HALF_UP).toPlainString();
    }
}
