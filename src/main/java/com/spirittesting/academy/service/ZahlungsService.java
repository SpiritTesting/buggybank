package com.spirittesting.academy.service;

import com.spirittesting.academy.domain.Euro;
import com.spirittesting.academy.domain.Konto;
import com.spirittesting.academy.domain.Zahlung;
import com.spirittesting.academy.exceptions.KontoDeckungException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ZahlungsService {

    private KontoService kontoService;

    public ZahlungsService(KontoService kontoService) {
        this.kontoService = kontoService;
    }

    public Zahlung addZahlung(String quellKontonummer, String zielKontonummer, Euro betrag) {
        Konto quelle = kontoService.getKonto(quellKontonummer);
        Konto ziel = kontoService.getKonto(zielKontonummer);
        Zahlung zahlung = new Zahlung(quelle, ziel, betrag);

        if (quelle.getBetrag().subtract(betrag).compareTo(quelle.getKreditrahmen().multiply(BigDecimal.valueOf(-1))) < 0) {
            throw new KontoDeckungException(quellKontonummer);
        }

        quelle.addZahlung(zahlung);
        ziel.addZahlung(zahlung);
        return zahlung;
    }

}
