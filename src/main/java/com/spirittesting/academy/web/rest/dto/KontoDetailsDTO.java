package com.spirittesting.academy.web.rest.dto;

import com.spirittesting.academy.domain.Euro;

import java.util.Objects;
import java.util.SortedSet;
import java.util.StringJoiner;
import java.util.TreeSet;

public class KontoDetailsDTO {

  private String kontonummer;
  private Euro kreditrahmen;
  private Euro betrag;
  private SortedSet<ZahlungDTO> zahlungen = new TreeSet<>();
  private String name;


  KontoDetailsDTO() {
  }

  public KontoDetailsDTO(String kontonummer, Euro kreditrahmen, Euro betrag, SortedSet<ZahlungDTO> zahlungen,
                         String name) {
    this.kontonummer = kontonummer;
    this.kreditrahmen = kreditrahmen;
    this.betrag = betrag;
    this.zahlungen = zahlungen;
    this.name = name;
  }

  public String getKontonummer() {
    return kontonummer;
  }

  public void setKontonummer(String kontonummer) {
    this.kontonummer = kontonummer;
  }

  public Euro getKreditrahmen() {
    return kreditrahmen;
  }

  public void setKreditrahmen(Euro kreditrahmen) {
    this.kreditrahmen = kreditrahmen;
  }

  public Euro getBetrag() {
    return betrag;
  }

  public void setBetrag(Euro betrag) {
    this.betrag = betrag;
  }

  public SortedSet<ZahlungDTO> getZahlungen() {
    return zahlungen;
  }

  public void setZahlungen(SortedSet<ZahlungDTO> zahlungen) {
    this.zahlungen = zahlungen;
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
    KontoDetailsDTO that = (KontoDetailsDTO) o;
    return Objects.equals(getKontonummer(), that.getKontonummer()) &&
      Objects.equals(getKreditrahmen(), that.getKreditrahmen()) &&
      Objects.equals(getBetrag(), that.getBetrag()) &&
      Objects.equals(getZahlungen(), that.getZahlungen()) &&
      Objects.equals(getName(), that.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getKontonummer(), getKreditrahmen(), getBetrag(), getZahlungen(), getName());
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", KontoDetailsDTO.class.getSimpleName() + "[", "]")
      .add("kontonummer='" + kontonummer + "'")
      .add("kreditrahmen=" + kreditrahmen)
      .add("betrag=" + betrag)
      .add("zahlungen=" + zahlungen)
      .add("name=" + name)
      .toString();
  }
}
