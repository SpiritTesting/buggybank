package com.spirittesting.academy.web.rest.dto;

import com.spirittesting.academy.domain.Euro;

import java.util.Objects;
import java.util.SortedSet;
import java.util.StringJoiner;
import java.util.TreeSet;

public class KundeDetailsDTO {

    private String kundennummer;
    private String name;
    private SortedSet<String> konten = new TreeSet<>();
    private Euro saldo;

    KundeDetailsDTO() {
    }

    public KundeDetailsDTO(String kundennummer, String name, SortedSet<String> konten, Euro saldo) {
        this.kundennummer = kundennummer;
        this.name = name;
        this.konten = konten;
        this.saldo = saldo;
    }

    public String getKundennummer() {
        return kundennummer;
    }

    public void setKundennummer(String kundennummer) {
        this.kundennummer = kundennummer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SortedSet<String> getKonten() {
        return konten;
    }

    public void setKonten(SortedSet<String> konten) {
        this.konten = konten;
    }

    public Euro getSaldo() {
        return saldo;
    }

    public void setSaldo(Euro saldo) {
        this.saldo = saldo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KundeDetailsDTO that = (KundeDetailsDTO) o;
        return Objects.equals(getKundennummer(), that.getKundennummer()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getKonten(), that.getKonten()) &&
                Objects.equals(getSaldo(), that.getSaldo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKundennummer(), getName(), getKonten(), getSaldo());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", KundeDetailsDTO.class.getSimpleName() + "[", "]")
                .add("kundennummer='" + kundennummer + "'")
                .add("name='" + name + "'")
                .add("kontonummer=" + konten)
                .add("saldo='" + saldo + "'")
                .toString();
    }
}
