package com.spirittesting.academy.dto;

import com.spirittesting.academy.domain.Konto;

import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class KontoInfo {

    private final String kontonummer;
    private final String betrag;
    private final Set<ZahlungsInfo> zahlungen = new HashSet<>();

    public KontoInfo(Konto konto) {
        this.kontonummer = konto.getKontonummer();
        this.betrag = konto.getBetrag().setScale(2, RoundingMode.HALF_UP).toPlainString();
        this.zahlungen.addAll(konto.getZahlungen().stream().map(ZahlungsInfo::new).collect(Collectors.toSet()));
    }

    public String getKontonummer() {
        return kontonummer;
    }

    public String getBetrag() {
        return betrag;
    }

    public Set<ZahlungsInfo> getZahlungen() {
        return zahlungen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KontoInfo kontoInfo = (KontoInfo) o;
        return getKontonummer().equals(kontoInfo.getKontonummer()) &&
                getBetrag().equals(kontoInfo.getBetrag()) &&
                getZahlungen().equals(kontoInfo.getZahlungen());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKontonummer(), getBetrag(), getZahlungen());
    }

    @Override
    public String toString() {
        return "KtoNr " + kontonummer + ", Saldo: EUR " + betrag + "\n" + zahlungen.stream().sorted().map(ZahlungsInfo::toString).map(z -> "\t" + z).collect(Collectors.joining("\n"));
    }
}
