package com.spirittesting.academy.service;

import com.spirittesting.academy.domain.Kunde;
import com.spirittesting.academy.exceptions.KundeNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class KundeService {

    private final Set<Kunde> kunden = new HashSet<>();

    private KundennummerService kundennummerService;

    public KundeService(KundennummerService kundennummerService) {
        this.kundennummerService = kundennummerService;
    }

    public Kunde addKunde(String name) {
        if (name == null) throw new IllegalArgumentException("Name darf nicht NULL sein");
        Kunde kunde = new Kunde(kundennummerService.next(), name);
        kunden.add(kunde);
        return kunde;
    }

    public Kunde findByKundennummer(String kundennummer) throws KundeNotFoundException {
        return kunden.stream().filter(kunde -> kundennummer.equals(kunde.getKundennummer())).findFirst().orElseThrow(() -> new KundeNotFoundException(kundennummer));
    }

    public Set<Kunde> findByName(String name) {
        return kunden.stream().filter(kunde -> kunde.getName().toLowerCase().startsWith(name.toLowerCase())).collect(Collectors.toSet());
    }

}
