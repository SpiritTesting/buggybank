package com.spirittesting.academy.domain;

import org.hibernate.annotations.WhereJoinTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

@Entity
public class Konto {

    private static Logger log = LoggerFactory.getLogger(Konto.class);

    @Id
    private String kontonummer;
    @ManyToOne
    @JoinColumn(name = "kunde", referencedColumnName = "kundennummer")
    private Kunde kunde;
    @Embedded
    @AttributeOverride(name="euro", column=@Column(name="kreditrahmen"))
    private Euro kreditrahmen = new Euro();

    Konto() {
    }

    public Konto(String kontonummer, Kunde kunde) {
        log.info("Konto wurde erstellt. Kontonummer {}, Kunde {}", kontonummer, kunde);
        this.kontonummer = kontonummer;
        this.kunde = kunde;
    }

    void setKontonummer(String kontonummer) {
        this.kontonummer = kontonummer;
    }

    void setKunde(Kunde kunde) {
        this.kunde = kunde;
    }

    public String getKontonummer() {
        return kontonummer;
    }

    public Kunde getKunde() {
        return kunde;
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
        return Objects.equals(getKontonummer(), konto.getKontonummer()) &&
                Objects.equals(getKunde(), konto.getKunde()) &&
                Objects.equals(getKreditrahmen(), konto.getKreditrahmen());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKontonummer(), getKunde(), getKreditrahmen());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Konto.class.getSimpleName() + "[", "]")
                .add("kontonummer='" + kontonummer + "'")
                .add("kunde=" + kunde.getKundennummer())
                .add("kreditrahmen=" + kreditrahmen)
                .toString();
    }
}
