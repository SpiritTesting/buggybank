package com.spirittesting.academy.web.rest.dto;

import com.spirittesting.academy.domain.Kunde;

import java.util.Objects;
import java.util.StringJoiner;

public class KundeDTO implements Comparable<KundeDTO> {

    private String kundennummer;
    private String name;

    KundeDTO() {
    }

    public KundeDTO(String kundennummer, String name) {
        this.kundennummer = kundennummer;
        this.name = name;
    }

    public static KundeDTO of(Kunde kunde) {
        return new KundeDTO(kunde.getKundennummer(), kunde.getName());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KundeDTO kundeDTO = (KundeDTO) o;
        return Objects.equals(getKundennummer(), kundeDTO.getKundennummer()) &&
                Objects.equals(getName(), kundeDTO.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKundennummer(), getName());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", KundeDTO.class.getSimpleName() + "[", "]")
                .add("kundennummer='" + kundennummer + "'")
                .add("name='" + name + "'")
                .toString();
    }

    @Override
    public int compareTo(KundeDTO o) {
        return kundennummer.compareTo(o.kundennummer);
    }
}
