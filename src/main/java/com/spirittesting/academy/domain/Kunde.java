package com.spirittesting.academy.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.StringJoiner;

public class Kunde {

    private static Logger log = LoggerFactory.getLogger(Kunde.class);
    private String kundennummer;
    private String name;

    public Kunde() {
        log.info("Leerer Kunde erzeugt");
    }

    public Kunde(String kundennummer, String name) {
        log.info("Kunde angelegt mit Kundennummer {} und Name {}", kundennummer, name);
        this.kundennummer = kundennummer;
        this.name = name;
    }

    public String getKundennummer() {
        return this.kundennummer;
    }

    public void setKundennummer(String kundennummer) {
        log.info("Kundennummer geändert von {} auf {}", this.kundennummer, kundennummer);
        this.kundennummer = kundennummer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        log.info("Name geändert von {} auf {}", this.name, name);
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kunde kunde = (Kunde) o;
        return Objects.equals(getKundennummer(), kunde.getKundennummer()) &&
                Objects.equals(getName(), kunde.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKundennummer(), getName());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Kunde.class.getSimpleName() + "[", "]")
                .add("kundennummer='" + kundennummer + "'")
                .add("name='" + name + "'")
                .toString();
    }
}
