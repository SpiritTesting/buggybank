package com.spirittesting.academy.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

@Entity
public class Zahlung {

    @Id
    private UUID id = UUID.randomUUID();
    private Instant datum = Instant.now();
    @ManyToOne
    @JoinColumn(name = "quelle", referencedColumnName = "kontonummer")
    private Konto quelle;
    @ManyToOne
    @JoinColumn(name = "ziel", referencedColumnName = "kontonummer")
    private Konto ziel;
    @Embedded
    @AttributeOverride(name = "euro", column = @Column(name = "betrag"))
    private Euro betrag;

    Zahlung() {}

    public Zahlung(Konto quelle, Konto ziel, Euro betrag) {
        if (quelle == null) {
            throw new IllegalArgumentException("Quellkonto darf nicht NULL sein");
        }
        this.quelle = quelle;

        if (ziel == null) {
            throw new IllegalArgumentException("Zielkonto darf nicht NULL sein");
        }
        this.ziel = ziel;

        if (betrag == null) {
            throw new IllegalArgumentException("Betrag darf nicht NULL sein");
        }
        this.betrag = betrag;
    }

    void setId(UUID id) {
        this.id = id;
    }

    UUID getId() { return id; }

    public Instant getDatum() {
        return datum;
    }

    public Konto getQuelle() {
        return quelle;
    }

    public Konto getZiel() {
        return ziel;
    }

    public Euro getBetrag() {
        return betrag;
    }

    void setDatum(Instant datum) {
        this.datum = datum;
    }

    void setQuelle(Konto quelle) {
        this.quelle = quelle;
    }

    void setZiel(Konto ziel) {
        this.ziel = ziel;
    }

    void setBetrag(Euro betrag) {
        this.betrag = betrag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zahlung zahlung = (Zahlung) o;
        return getDatum().equals(zahlung.getDatum()) &&
                Objects.equals(getQuelle().getKontonummer(), zahlung.getQuelle().getKontonummer()) &&
                Objects.equals(getZiel().getKontonummer(), zahlung.getZiel().getKontonummer()) &&
                getBetrag().equals(zahlung.getBetrag());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDatum(), getQuelle().getKontonummer(), getZiel().getKontonummer(), getBetrag());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Zahlung.class.getSimpleName() + "[", "]")
                .add("datum=" + datum)
                .add("quelle=" + quelle.getKontonummer())
                .add("ziel=" + ziel.getKontonummer())
                .add("betrag=" + betrag)
                .toString();
    }
}
