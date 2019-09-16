package com.spirittesting.academy.web.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spirittesting.academy.domain.Euro;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;
import java.util.StringJoiner;

public class ZahlungDTO implements Comparable<ZahlungDTO> {

  @JsonIgnore
  private Instant preciseDatum;
  private LocalDate datum;
  private String quelle;
  private String ziel;
  private Euro betrag;
  private String zweck;

  public ZahlungDTO() {
  }

  public ZahlungDTO(Instant datum, String quelle, String ziel, Euro betrag, String zweck) {
    this.preciseDatum = datum;
    this.datum = datum.atZone(ZoneId.systemDefault()).toLocalDate();
    this.quelle = quelle;
    this.ziel = ziel;
    this.betrag = betrag;
    this.zweck = zweck;
  }

  public LocalDate getDatum() {
    return datum;
  }

  public void setDatum(LocalDate datum) {
    this.datum = datum;
  }

  public String getQuelle() {
    return quelle;
  }

  public void setQuelle(String quelle) {
    this.quelle = quelle;
  }

  public String getZiel() {
    return ziel;
  }

  public void setZiel(String ziel) {
    this.ziel = ziel;
  }

  public Euro getBetrag() {
    return betrag;
  }

  public void setBetrag(Euro betrag) {
    this.betrag = betrag;
  }

  public void setBetrag(String betrag) { this.betrag = new Euro(betrag); }

  Instant getPreciseDatum() {
    return preciseDatum;
  }

  void setPreciseDatum(Instant preciseDatum) {
    this.preciseDatum = preciseDatum;
  }

  public String getZweck() {
    return zweck;
  }

  public void setZweck(String zweck) {
    this.zweck = zweck;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ZahlungDTO that = (ZahlungDTO) o;
    return Objects.equals(getDatum(), that.getDatum()) &&
      Objects.equals(getQuelle(), that.getQuelle()) &&
      Objects.equals(getZiel(), that.getZiel()) &&
      Objects.equals(getBetrag(), that.getBetrag()) &&
      Objects.equals(getZweck(), that.getZweck());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getDatum(), getQuelle(), getZiel(), getBetrag(), getZweck());
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", ZahlungDTO.class.getSimpleName() + "[", "]")
      .add("datum=" + datum)
      .add("quelle='" + quelle + "'")
      .add("ziel='" + ziel + "'")
      .add("betrag=" + betrag)
      .add("zweck=" + zweck == null ? "Keine Angabe" : zweck)
      .toString();
  }

  @Override
  public int compareTo(ZahlungDTO o) {
    return preciseDatum.compareTo(o.preciseDatum);
  }
}
