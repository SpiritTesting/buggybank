package com.spirittesting.academy.dto;

import com.spirittesting.academy.domain.Konto;
import com.spirittesting.academy.domain.Kunde;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class KundenInfoTest {

    Kunde kunde = new Kunde("1", "Hannes");
    List<Konto> konten = Arrays.asList(new Konto("1", kunde), new Konto("2", kunde), new Konto("3", new Kunde()));
    KundenInfo kundenInfo = new KundenInfo(kunde.getKundennummer(), kunde.getName(), konten);

    @Test
    void getKundennummer() {
        assertEquals(kunde.getKundennummer(), kundenInfo.getKundennummer());
    }

    @Test
    void getName() {
        assertEquals(kunde.getName(), kundenInfo.getName());
    }

    @Test
    void getKonten() {
        assertEquals(2, kundenInfo.getKonten().size());
        assertNotNull(kundenInfo.getKonten().get("1"));
        assertNotNull(kundenInfo.getKonten().get("2"));
    }
}
