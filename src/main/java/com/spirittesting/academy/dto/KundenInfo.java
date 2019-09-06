package com.spirittesting.academy.dto;

import com.spirittesting.academy.domain.Konto;

import java.math.RoundingMode;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class KundenInfo {

    private final String kundennummer;
    private final String name;
    private final Map<String, String> konten = new HashMap<>();

    public KundenInfo(String kundennummer, String name, Collection<Konto> konten) {
        this.kundennummer = kundennummer;
        this.name = name;
        this.addKonten(konten);
    }

    private void addKonten(Collection<Konto> konten) {
        for (Konto konto : konten) {
            if (!kundennummer.equals(konto.getKunde().getKundennummer())) continue;
            this.konten.put(konto.getKontonummer(), konto.getBetrag().setScale(2, RoundingMode.HALF_UP).toPlainString());
        }
    }

    public String getKundennummer() {
        return kundennummer;
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getKonten() {
        return konten;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KundenInfo that = (KundenInfo) o;
        return Objects.equals(getKundennummer(), that.getKundennummer()) &&
                Objects.equals(getName(), that.getName()) &&
                getKonten().equals(that.getKonten());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKundennummer(), getName(), getKonten());
    }

    @Override
    public String toString() {
        return "Kunde " + name + " (KdNr. " + kundennummer + ")\n" + konten.entrySet().stream().map(entry ->
                "\tKtoNr " + entry.getKey() + ": EUR " + entry.getValue()).collect(Collectors.joining("\n"));
    }
}
