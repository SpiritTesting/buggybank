package com.spirittesting.academy.dto;

import com.spirittesting.academy.domain.Zahlung;

import java.math.RoundingMode;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class ZahlungsInfo implements Comparable<ZahlungsInfo> {

    private final String datum;
    private final String quelle;
    private final String ziel;
    private final String betrag;


    public ZahlungsInfo(Zahlung zahlung) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy").withZone(ZoneId.systemDefault());
        this.datum = formatter.format(zahlung.getDatum());
        this.quelle = zahlung.getQuelle().getKontonummer();
        this.ziel = zahlung.getZiel().getKontonummer();
        this.betrag = zahlung.getBetrag().toString();
    }

    public String getDatum() {
        return datum;
    }

    public String getQuelle() {
        return quelle;
    }

    public String getZiel() {
        return ziel;
    }

    public String getBetrag() {
        return betrag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZahlungsInfo that = (ZahlungsInfo) o;
        return getDatum().equals(that.getDatum()) &&
                getQuelle().equals(that.getQuelle()) &&
                getZiel().equals(that.getZiel()) &&
                getBetrag().equals(that.getBetrag());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDatum(), getQuelle(), getZiel(), getBetrag());
    }

    @Override
    public String toString() {
        return String.format("%s: %s -> %s (%s)", datum, quelle, ziel, betrag);
    }

    @Override
    public int compareTo(ZahlungsInfo o) {
        return datum.compareTo(o.datum);
    }
}
