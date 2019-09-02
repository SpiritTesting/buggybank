package com.spirittesting.academy;

import com.spirittesting.academy.domain.Konto;
import com.spirittesting.academy.domain.Kunde;
import com.spirittesting.academy.domain.Zahlung;
import com.spirittesting.academy.dto.KontoInfo;
import com.spirittesting.academy.dto.KundenInfo;
import com.spirittesting.academy.exceptions.KontoNotFoundException;
import com.spirittesting.academy.exceptions.KundeNotFoundException;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class KundenService {

    private final Set<Kunde> kunden = new HashSet<>();
    private final Set<Konto> konten = new HashSet<>();
    private final AtomicInteger kundennummerSequence = new AtomicInteger();
    private final AtomicInteger kontonummerSequence = new AtomicInteger();


    public void addZahlung(Konto quelle, Konto ziel, BigDecimal betrag) {
        Zahlung zahlung = new Zahlung(quelle, ziel, betrag);
        quelle.addZahlung(zahlung);
        ziel.addZahlung(zahlung);
    }

    public Kunde addKunde(String name, int anzahlKonten) {
        Kunde kunde = new Kunde(String.format("%03d", kundennummerSequence.incrementAndGet()), name);
        for (int i = 0; i < anzahlKonten; i++) {
            Konto konto = new Konto(String.format("%02d", kontonummerSequence.incrementAndGet()), kunde);
            konten.add(konto);
        }
        kunden.add(kunde);
        return kunde;
    }

    public Konto getKonto(String kontonummer) throws KontoNotFoundException {
        return konten.stream().filter(konto -> kontonummer.equals(konto.getKontonummer())).findFirst().orElseThrow(() -> new KontoNotFoundException(kontonummer));
    }

    public KundenInfo getKundeninfo(String kundennummer) throws KundeNotFoundException {
        for (Kunde kunde : kunden) {
            if (kundennummer.equals(kunde.getKundennummer())) {
                return new KundenInfo(kundennummer, kunde.getName(), konten);
            }
        }
        throw new KundeNotFoundException(kundennummer);
    }

    public KontoInfo getKontoinfo(String kontonummer) throws KontoNotFoundException {
        return new KontoInfo(konten.stream()
                .filter(konto -> kontonummer.equals(konto.getKontonummer()))
                .findFirst()
                .orElseThrow(() -> new KontoNotFoundException(kontonummer)));
    }


}
