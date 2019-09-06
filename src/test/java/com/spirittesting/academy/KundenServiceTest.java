package com.spirittesting.academy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class KundenServiceTest {

    @Nested
    @DisplayName("addZahlung")
    class AddZahlung {

        @Test
        void quelleIsNull() {
        }

        @Test
        void zielIsNull() {
        }

        @Test
        void betragIsNull() {
        }

        @Test
        void quelleIstGleichZiel() {
        }

        @Test
        void zahlungInQuelle() {
        }

        @Test
        void zahlungInZiel() {
        }

    }

    @Nested
    @DisplayName("addKunde")
    class AddKunde {

        @Test
        void erhaeltKundennummer() {
        }

        @Test
        void erhaeltKontonummer() {
        }

        @Test
        void mehrereKontenWerdenAngelegt() {
        }

        @Test
        void nameIsNull() {
        }

        @Test
        void anzahlKontenIstNegativ() {
        }

    }

    @Nested
    @DisplayName("getKonto")
    class GetKonto {
        @Test
        void gefunden() {
        }

        @Test
        void nichtVorhanden() {
        }

        @Test
        void isNullSafe() {
        }
    }

    @Nested
    @DisplayName("getKundeninfo")
    class GetKundeninfo {
        @Test
        void gefunden() {
        }

        @Test
        void nichtVorhanden() {
        }

        @Test
        void isNullSafe() {
        }
    }

    @Nested
    @DisplayName("getKontoinfo")
    class GetKontoinfo {
        @Test
        void gefunden() {
        }

        @Test
        void nichtVorhanden() {
        }

        @Test
        void isNullSafe() {
        }
    }

}
