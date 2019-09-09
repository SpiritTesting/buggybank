package com.spirittesting.academy.service;

import com.spirittesting.academy.domain.Konto;
import com.spirittesting.academy.domain.Kunde;
import com.spirittesting.academy.exceptions.KontoNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class KontoService {

    private KontonummerService kontonummerService;
    private Set<Konto> konten = new HashSet<>();

    public KontoService(KontonummerService kontonummerService) {
        this.kontonummerService = kontonummerService;
    }

    public Konto addKonto(Kunde kunde) {
        if (kunde == null) throw new IllegalArgumentException("Kunde darf nicht NULL sein");
        Konto konto = new Konto(kontonummerService.next(), kunde);
        this.konten.add(konto);
        return konto;
    }

    Konto addKonto(String kontonummer) {
        Konto konto = new Konto(kontonummer, new Kunde("000", "Buggybank"));
        this.konten.add(konto);
        return konto;
    }

    public Konto getKonto(String kontonummer) throws KontoNotFoundException {
        return konten.stream().filter(konto -> kontonummer.equals(konto.getKontonummer())).findFirst().orElseThrow(() -> new KontoNotFoundException(kontonummer));
    }

    public Set<Konto> getKonten(String kundennummer) {
        return konten.stream().filter(konto -> kundennummer.equals(konto.getKunde().getKundennummer())).collect(Collectors.toSet());
    }

    Set<Konto> getAllKonten() {
        return konten;
    }
}
