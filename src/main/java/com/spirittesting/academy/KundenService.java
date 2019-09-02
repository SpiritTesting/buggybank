package com.spirittesting.academy;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class KundenService {

    private final Set<Kunde> kunden = new HashSet<>();
    private final Set<Konto> konten = new HashSet<>();

    public Set<Kunde> getKunden() {
        return Collections.unmodifiableSet(kunden);
    }

    public Set<Konto> getKonten() {
        return Collections.unmodifiableSet(konten);
    }

    public void addKunde(Kunde kunde) {
        this.kunden.add(kunde);
    }

    public void addKonto(Konto konto) {
        this.konten.add(konto);
    }

    public String getKundeninfo(String kundennummer) {
        for (Kunde kunde : kunden) {
            if (kundennummer.equals(kunde.getKundennummer())) {
                String ausgabe = kunde.toString();
                ausgabe += "\n\n";
                for (Konto konto : konten) {
                    if (kunde.equals(konto.getKunde())) {
                        ausgabe += konto.toString() + "\n";
                    }
                }
                return ausgabe;
            }
        }
        return "KEIN KUNDE MIT DIESER KUNDENNUMMER GEFUNDEN";
    }

    public String getKontoinfo(String kontonummer) {
        for (Konto konto : konten) {
            if (kontonummer.equals(konto.getKontonummer())) {
                String ausgabe = konto.toString() + "\n\n";
                for (Zahlung zahlung : konto.getZahlungen()) {
                    ausgabe += zahlung.toString() + "\n";
                }
                return ausgabe;
            }
        }
        return "KEIN KONTO MIT DIESER KONTONUMMER GEFUNDEN";
    }



}
