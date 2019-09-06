package com.spirittesting.academy.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;

public class Konto {

    private static Logger log = LoggerFactory.getLogger(Konto.class);
    private final Set<Zahlung> zahlungen = new HashSet<>();
    private String kontonummer;
    private Kunde kunde;

    public Konto() {
        log.info("Leeres Konto wurde erstellt.");
    }

    public Konto(String kontonummer, Kunde kunde) {
        log.info("Konto wurde erstellt. Kontonummer {}, Kunde {}", kontonummer, kunde);
        this.kontonummer = kontonummer;
        this.kunde = kunde;
    }

    public String getKontonummer() {
        return kontonummer;
    }

    public void setKontonummer(String kontonummer) {
        log.info("Kontonummer geändert von {} auf {}", this.kontonummer, kontonummer);
        this.kontonummer = kontonummer;
    }

    public Kunde getKunde() {
        return kunde;
    }

    public void setKunde(Kunde kunde) {
        log.info("Kunde geändert von {} auf {}", this.kunde, kunde);
        this.kunde = kunde;
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

    public BigDecimal getBetrag() {
        BigDecimal betrag = BigDecimal.ZERO;
        for (Zahlung zahlung : zahlungen) {
            if (Objects.equals(zahlung.getQuelle().getKontonummer(), kontonummer)) {
                betrag = betrag.subtract(zahlung.getBetrag());
            }
            if (Objects.equals(zahlung.getZiel().getKontonummer(), kontonummer)) {
                betrag = betrag.add(zahlung.getBetrag());
            }
        }
        return betrag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Konto konto = (Konto) o;
        return Objects.equals(getKontonummer(), konto.getKontonummer()) &&
                Objects.equals(getKunde(), konto.getKunde()) &&
                getZahlungen().equals(konto.getZahlungen());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKontonummer(), getKunde(), getZahlungen());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Konto.class.getSimpleName() + "[", "]")
                .add("kontonummer='" + kontonummer + "'")
                .add("kunde=" + kunde)
                .add("betrag=" + getBetrag())
                .toString();
    }
}
