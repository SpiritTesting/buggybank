package com.spirittesting.academy.service;

import com.spirittesting.academy.domain.Euro;
import com.spirittesting.academy.domain.Konto;
import com.spirittesting.academy.domain.Zahlung;
import com.spirittesting.academy.exceptions.KontoDeckungException;
import com.spirittesting.academy.repository.ZahlungRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class ZahlungsService {

    private KontoService kontoService;
    private ZahlungRepository zahlungRepository;

    public ZahlungsService(KontoService kontoService, ZahlungRepository zahlungRepository) {
        this.kontoService = kontoService;
        this.zahlungRepository = zahlungRepository;
    }

    @Transactional
    public Zahlung addZahlung(String quellKontonummer, String zielKontonummer, Euro betrag) {
        return addZahlung(quellKontonummer, zielKontonummer, betrag, false);
    }

    @Transactional
    public List<Zahlung> getZahlungen(String kontonummer) {
        final List<Zahlung> zahlungen = zahlungRepository.findAllByQuelle_Kontonummer(kontonummer);
        zahlungen.addAll(zahlungRepository.findAllByZiel_Kontonummer(kontonummer));
        return zahlungen;
    }

    Zahlung addZahlung(String quellKontonummer, String zielKontonummer, Euro betrag, boolean ignoreKreditrahmen) {
        Konto quelle = kontoService.getKonto(quellKontonummer);
        Konto ziel = kontoService.getKonto(zielKontonummer);
        Zahlung zahlung = new Zahlung(quelle, ziel, betrag);

        if (!ignoreKreditrahmen && kontoService.getBetrag(quelle).subtract(betrag).compareTo(quelle.getKreditrahmen().multiply(BigDecimal.valueOf(-1))) < 0) {
            throw new KontoDeckungException(quellKontonummer);
        }

        return zahlungRepository.save(zahlung);
    }

}
