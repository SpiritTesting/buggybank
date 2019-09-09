package com.spirittesting.academy.service;

import com.spirittesting.academy.domain.Euro;
import com.spirittesting.academy.domain.Konto;
import com.spirittesting.academy.domain.Kunde;
import com.spirittesting.academy.domain.Zahlung;
import com.spirittesting.academy.exceptions.KontoDeckungException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ZahlungsServiceTestIT {

    @Autowired
    ZahlungsService zahlungsService;
    @Autowired
    KontoService kontoService;
    @Autowired
    KundeService kundeService;

    @Test
    void addZahlung() {
        final Kunde hannes = kundeService.addKunde("Hannes");
        final Kunde werner = kundeService.addKunde("Werner");

        final Konto kontoHannes = kontoService.addKonto(hannes);
        final Konto kontoWerner = kontoService.addKonto(werner);

        kontoHannes.setKreditrahmen(new Euro(200));

        final Zahlung zahlung = zahlungsService.addZahlung(kontoHannes.getKontonummer(), kontoWerner.getKontonummer(), new Euro(200));
        assertEquals(new Euro(-200), kontoService.getKonto(kontoHannes.getKontonummer()).getBetrag());
        assertEquals(new Euro(200), kontoService.getKonto(kontoWerner.getKontonummer()).getBetrag());

        assertThrows(KontoDeckungException.class, () -> zahlungsService.addZahlung(kontoHannes.getKontonummer(),
                kontoWerner.getKontonummer(), new Euro(0, 1)));
    }

}
