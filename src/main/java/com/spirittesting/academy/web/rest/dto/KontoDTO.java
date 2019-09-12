package com.spirittesting.academy.web.rest.dto;

import com.spirittesting.academy.domain.Euro;

import java.util.Objects;
import java.util.StringJoiner;

public class KontoDTO implements Comparable<KontoDTO> {

    private String kontonummer;
    private Euro betrag;

    KontoDTO() {
    }

    public String getKontonummer() {
        return kontonummer;
    }

    public void setKontonummer(String kontonummer) {
        this.kontonummer = kontonummer;
    }

    public Euro getBetrag() {
        return betrag;
    }

    public void setBetrag(Euro betrag) {
        this.betrag = betrag;
    }

    public KontoDTO(String kontonummer, Euro betrag) {
        this.kontonummer = kontonummer;
        this.betrag = betrag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KontoDTO kontoDTO = (KontoDTO) o;
        return Objects.equals(getKontonummer(), kontoDTO.getKontonummer()) &&
                Objects.equals(getBetrag(), kontoDTO.getBetrag());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKontonummer(), getBetrag());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", KontoDTO.class.getSimpleName() + "[", "]")
                .add("kontonummer='" + kontonummer + "'")
                .add("betrag='" + betrag + "'")
                .toString();
    }

    @Override
    public int compareTo(KontoDTO o) {
        return kontonummer.compareTo(o.kontonummer);
    }

}
