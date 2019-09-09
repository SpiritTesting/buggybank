package com.spirittesting.academy.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;

public class Konto {

    private static Logger log = LoggerFactory.getLogger(Konto.class);
    private final Set<Zahlung> zahlungen = new HashSet<>();
    private final String kontonummer;
    private final Kunde kunde;
    private Euro kreditrahmen = new Euro();

    public Konto(String kontonummer, Kunde kunde) {
        log.info("Konto wurde erstellt. Kontonummer {}, Kunde {}", kontonummer, kunde);
        this.kontonummer = kontonummer;
        this.kunde = kunde;
    }

    public String getKontonummer() {
        return kontonummer;
    }

    public Kunde getKunde() {
        return kunde;
    }

    public Set<Zahlung> getZahlungen() {
        return Collections.unmodifiableSet(zahlungen);
    }

    public void addZahlung(Zahlung zahlung) {
        if (zahlung == null) throw new IllegalArgumentException("Zahlung darf nicht NULL sein");
        log.info("Zahlung hinzugefügt zu {}: {}", this.kontonummer, zahlung);
        this.zahlungen.add(zahlung);
    }

    public void removeZahlung(Zahlung zahlung) {
        if (zahlung == null) throw new IllegalArgumentException("Zahlung darf nicht NULL sein");
        log.info("Zahlung entfernt aus {}: {}", this.kontonummer, zahlung);
        this.zahlungen.remove(zahlung);
    }

    public Euro getBetrag() {
        BigDecimal betrag = BigDecimal.ZERO;
        for (Zahlung zahlung : zahlungen) {
            if (Objects.equals(zahlung.getQuelle().getKontonummer(), kontonummer)) {
                betrag = betrag.subtract(zahlung.getBetrag());
            }
            if (Objects.equals(zahlung.getZiel().getKontonummer(), kontonummer)) {
                betrag = betrag.add(zahlung.getBetrag());
            }
        }
        return new Euro(betrag);
    }

    public Euro getKreditrahmen() {
        return kreditrahmen;
    }

    public void setKreditrahmen(Euro kreditrahmen) {
        log.info("Kreditrahmen für Konto {} von EUR {} auf {} geändert.", kontonummer, this.kreditrahmen, kreditrahmen);
        this.kreditrahmen = kreditrahmen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Konto konto = (Konto) o;
        return Objects.equals(getZahlungen(), konto.getZahlungen()) &&
                Objects.equals(getKontonummer(), konto.getKontonummer()) &&
                Objects.equals(getKunde(), konto.getKunde()) &&
                Objects.equals(getKreditrahmen(), konto.getKreditrahmen());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getZahlungen(), getKontonummer(), getKunde(), getKreditrahmen());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Konto.class.getSimpleName() + "[", "]")
                .add("betrag=" + getBetrag())
                .add("kontonummer='" + kontonummer + "'")
                .add("kunde=" + kunde.getKundennummer())
                .add("kreditrahmen=" + kreditrahmen)
                .toString();
    }
}
