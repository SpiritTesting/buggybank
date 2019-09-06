package com.spirittesting.academy.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class KundeTest {

    private Kunde kundeLeer = new Kunde();
    private Kunde kundeVoll = new Kunde("123", "Hannes");

    @Test
    void getKundennummerNull() {
        assertNull(kundeLeer.getKundennummer());
    }

    @Test
    void getKundennummerBelegt() {
        assertEquals("123", kundeVoll.getKundennummer());
    }

    @Test
    void setKundennummer() {
        kundeLeer.setKundennummer("666");
        assertEquals("666", kundeLeer.getKundennummer());
    }

    @Test
    void getNameLeer() {
        assertNull(kundeLeer.getName());
    }

    @Test
    void getNameBelegt() {
        assertEquals("Hannes", kundeVoll.getName());
    }

    @Test
    void setName() {
        kundeLeer.setName("Werner");
        assertEquals("Werner", kundeLeer.getName());
    }

}
