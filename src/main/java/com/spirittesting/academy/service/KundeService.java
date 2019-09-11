package com.spirittesting.academy.service;

import com.spirittesting.academy.domain.Kunde;
import com.spirittesting.academy.exceptions.KundeNotFoundException;
import com.spirittesting.academy.repository.KundeRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class KundeService {

    private KundeRepository kundeRepository;
    private KundennummerService kundennummerService;

    public KundeService(KundennummerService kundennummerService, KundeRepository kundeRepository) {
        this.kundennummerService = kundennummerService;
        this.kundeRepository = kundeRepository;
    }

    public Kunde addKunde(String name) {
        if (name == null) throw new IllegalArgumentException("Name darf nicht NULL sein");
        Kunde kunde = new Kunde(kundennummerService.next(), name);
        kunde = kundeRepository.save(kunde);
        return kunde;
    }

    public Kunde findByKundennummer(String kundennummer) throws KundeNotFoundException {
        return kundeRepository.findById(kundennummer).orElseThrow(() -> new KundeNotFoundException(kundennummer));
    }

    public Set<Kunde> findByName(String name) {
        return kundeRepository.findAllByNameStartingWith(name);
    }

}
