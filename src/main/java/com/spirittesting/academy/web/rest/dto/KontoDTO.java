package com.spirittesting.academy.web.rest.dto;

import com.spirittesting.academy.domain.Euro;

import java.util.Objects;
import java.util.StringJoiner;

public class KontoDTO implements Comparable<KontoDTO> {

  private String kontonummer;
  private Euro betrag;
  private String name;

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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public KontoDTO(String kontonummer, Euro betrag, String name) {
    this.kontonummer = kontonummer;
    this.betrag = betrag;
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    KontoDTO kontoDTO = (KontoDTO) o;
    return Objects.equals(getKontonummer(), kontoDTO.getKontonummer()) &&
      Objects.equals(getBetrag(), kontoDTO.getBetrag()) &&
      Objects.equals(getName(), kontoDTO.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getKontonummer(), getBetrag(), getName());
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", KontoDTO.class.getSimpleName() + "[", "]")
      .add("kontonummer='" + kontonummer + "'")
      .add("betrag='" + betrag + "'")
      .add("name='" + (name == null ? "-ohne-'" : name + "'"))
      .toString();
  }

  @Override
  public int compareTo(KontoDTO o) {
    return kontonummer.compareTo(o.kontonummer);
  }

}
