package com.spirittesting.academy.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Embeddable
public class Euro implements Comparable<Euro> {

    public static Euro ZERO = new Euro(0);

    public Euro() {
        euro = BigDecimal.ZERO;
    }

    public Euro(String value) {
        euro = new BigDecimal(value.startsWith("EUR ") ? value.substring(4).toCharArray() : value.toCharArray());
    }

    public Euro(int euro) {
        this(euro, 0);
    }

    public Euro(int euro, int cent) {
        this.euro = new BigDecimal(Integer.toString(euro) + "." + Integer.toString(cent));
    }

    public Euro(BigDecimal value) {
        this.euro = value;
    }

    @Column(name = "betrag")
    private BigDecimal euro;

    public Euro multiply(BigDecimal multiplicand) {
        return new Euro(euro.multiply(multiplicand).setScale(2, RoundingMode.HALF_UP));
    }

    public Euro subtract(Euro subtrahend) {
        return new Euro(euro.subtract(subtrahend.euro));
    }

    public Euro add(Euro summand) {
        return new Euro(euro.add(summand.euro));
    }

    public boolean isNegative() { return euro.compareTo(BigDecimal.ZERO) < 0; }

    public Euro absolute() { return isNegative() ? new Euro(euro.multiply(BigDecimal.valueOf(-1))) : this; }

    @Override
    public String toString() {
        return "EUR " + euro.setScale(2, RoundingMode.HALF_UP).toPlainString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Euro euro1 = (Euro) o;
        return Objects.equals(euro.setScale(3, RoundingMode.HALF_UP).toPlainString(), euro1.euro.setScale(3, RoundingMode.HALF_UP).toPlainString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(euro);
    }

    @Override
    public int compareTo(Euro o) {
        return euro.compareTo(o.euro);
    }
}
