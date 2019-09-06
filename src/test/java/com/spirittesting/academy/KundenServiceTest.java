package com.spirittesting.academy;

import com.spirittesting.academy.domain.Konto;
import com.spirittesting.academy.exceptions.KontoNotFoundException;
import com.spirittesting.academy.exceptions.KundeNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class KundenServiceTest {

    KundenService kundenService = new KundenService();

    @BeforeEach
    void setup() {
        kundenService.addKunde("Hannes", 1);
        kundenService.addKunde("Werner", 2);
        kundenService.addZahlung(getKonto(1), getKonto(2), BigDecimal.TEN);
    }

    private Konto getKonto(int kontonummer) {
        try {
            return kundenService.getKonto(String.format("%02d", kontonummer));
        } catch (KontoNotFoundException e) {
            fail();
            return new Konto();
        }
    }

    @Nested
    @DisplayName("addZahlung")
    class AddZahlung {

        @Test
        void quelleIsNull() {
            assertThrows(IllegalArgumentException.class, () -> kundenService.addZahlung(null, new Konto(),
                    BigDecimal.ZERO));
        }

        @Test
        void zielIsNull() {
            assertThrows(IllegalArgumentException.class, () -> kundenService.addZahlung(new Konto(), null,
                    BigDecimal.ZERO));
        }

        @Test
        void betragIsNull() {
            assertThrows(IllegalArgumentException.class, () -> kundenService.addZahlung(new Konto(), new Konto(),
                    null));
        }

        @Test
        void quelleIstGleichZiel() {
            final BigDecimal betrag = getKonto(1).getBetrag();
            final int size = getKonto(1).getZahlungen().size();

            kundenService.addZahlung(getKonto(1), getKonto(1), BigDecimal.TEN);

            assertEquals(betrag, getKonto(1).getBetrag());
            assertEquals(size + 1, getKonto(1).getZahlungen().size());
        }

        @Test
        void zahlungInQuelle() {
            assertEquals(1, getKonto(1).getZahlungen().size());
        }

        @Test
        void zahlungInZiel() {
            assertEquals(1, getKonto(2).getZahlungen().size());
        }

    }

    @Nested
    @DisplayName("addKunde")
    class AddKunde {

        @Test
        void erhaeltKundennummer() throws KundeNotFoundException {
            assertNotNull(kundenService.getKundeninfo("001"));
        }

        @Test
        void erhaeltKontonummer() {
            assertNotNull(getKonto(1));
        }

        @Test
        void mehrereKontenWerdenAngelegt() throws KundeNotFoundException {
            assertEquals(2, kundenService.getKundeninfo("002").getKonten().size());
        }

        @Test
        void nameIsNull() {
            assertThrows(IllegalArgumentException.class, () -> kundenService.addKunde(null, 1));
        }

        @Test
        void anzahlKontenIstNegativ() {
            assertThrows(IllegalArgumentException.class, () -> kundenService.addKunde("Dieter", -1));
        }

    }

    @Nested
    @DisplayName("getKonto")
    class GetKonto {
        @Test
        void gefunden() {
            assertNotNull(getKonto(1));
        }

        @Test
        void nichtVorhanden() throws KontoNotFoundException {
            assertThrows(KontoNotFoundException.class, () -> kundenService.getKonto("04"));
        }

        @Test
        void isNullSafe() {
            assertThrows(IllegalArgumentException.class, () -> kundenService.getKonto(null));
        }
    }

    @Nested
    @DisplayName("getKundeninfo")
    class GetKundeninfo {
        @Test
        void gefunden() throws KundeNotFoundException {
            assertNotNull(kundenService.getKundeninfo("001"));
        }

        @Test
        void nichtVorhanden() {
            assertThrows(KundeNotFoundException.class, () -> kundenService.getKundeninfo("003"));
        }

        @Test
        void isNullSafe() {
            assertThrows(IllegalArgumentException.class, () -> kundenService.getKundeninfo(null));
        }
    }

    @Nested
    @DisplayName("getKontoinfo")
    class GetKontoinfo {
        @Test
        void gefunden() throws KontoNotFoundException {
            assertNotNull(kundenService.getKontoinfo("01"));
        }

        @Test
        void nichtVorhanden() {
            assertThrows(KontoNotFoundException.class, () -> kundenService.getKontoinfo("04"));
        }

        @Test
        void isNullSafe() {
            assertThrows(IllegalArgumentException.class, () -> kundenService.getKontoinfo(null));
        }
    }

}
