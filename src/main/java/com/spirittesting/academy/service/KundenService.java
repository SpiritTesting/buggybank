package com.spirittesting.academy.service;

import com.spirittesting.academy.domain.Konto;
import com.spirittesting.academy.domain.Kunde;
import com.spirittesting.academy.domain.Zahlung;
import com.spirittesting.academy.dto.KontoInfo;
import com.spirittesting.academy.dto.KundenInfo;
import com.spirittesting.academy.exceptions.KontoNotFoundException;
import com.spirittesting.academy.exceptions.KundeNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Service
public class KundenService {

    private final Set<Kunde> kunden = new HashSet<>();
    private final Set<Konto> konten = new HashSet<>();

    private KontonummerService kontonummerService;
    private KundennummerService kundennummerService;

    public KundenService(KontonummerService kontonummerService, KundennummerService kundennummerService) {
        this.kontonummerService = kontonummerService;
        this.kundennummerService = kundennummerService;
    }


    public void addZahlung(Konto quelle, Konto ziel, BigDecimal betrag) {
        Zahlung zahlung = new Zahlung(quelle, ziel, betrag);
        quelle.addZahlung(zahlung);
        ziel.addZahlung(zahlung);
    }

    public Kunde addKunde(String name, int anzahlKonten) {
        if (name == null) throw new IllegalArgumentException("Name darf nicht NULL sein");
        if (anzahlKonten < 0) throw new IllegalArgumentException("Anzahl Konten darf nicht negativ sein");
        Kunde kunde = new Kunde(kundennummerService.next(), name);
        for (int i = 0; i < anzahlKonten; i++) {
            Konto konto = new Konto(kontonummerService.next(), kunde);
            konten.add(konto);
        }
        kunden.add(kunde);
        return kunde;
    }

    public Konto getKonto(String kontonummer) throws KontoNotFoundException {
        if (kontonummer == null) throw new IllegalArgumentException("Kontonummer darf nicht NULL sein");
        return konten.stream().filter(konto -> kontonummer.equals(konto.getKontonummer())).findFirst().orElseThrow(() -> new KontoNotFoundException(kontonummer));
    }

    public KundenInfo getKundeninfo(String kundennummer) throws KundeNotFoundException {
        if (kundennummer == null) throw new IllegalArgumentException("Kundennummer darf nicht null sein");
        for (Kunde kunde : kunden) {
            if (kundennummer.equals(kunde.getKundennummer())) {
                return new KundenInfo(kundennummer, kunde.getName(), konten);
            }
        }
        throw new KundeNotFoundException(kundennummer);
    }

    public KontoInfo getKontoinfo(String kontonummer) throws KontoNotFoundException {
        if (kontonummer == null) throw new IllegalArgumentException("Kontonummer darf nicht NULL sein");
        return new KontoInfo(konten.stream()
                .filter(konto -> kontonummer.equals(konto.getKontonummer()))
                .findFirst()
                .orElseThrow(() -> new KontoNotFoundException(kontonummer)));
    }


}
