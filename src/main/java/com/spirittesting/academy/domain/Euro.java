package com.spirittesting.academy.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!o.getClass().isAssignableFrom(Euro.class)) return false;
        BigDecimal bigDecimal = (BigDecimal) o;
        return Objects.equals(super.setScale(3, RoundingMode.HALF_UP).toPlainString(),
                ((BigDecimal) o).setScale(3, RoundingMode.HALF_UP).toPlainString());
    }
}
