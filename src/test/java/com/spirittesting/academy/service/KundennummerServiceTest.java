package com.spirittesting.academy.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KundennummerServiceTest {

    @Test
    void next() {
        KundennummerService kundennummerService = new KundennummerService(10, "%d");
        assertEquals("10", kundennummerService.next());
        assertEquals("11", kundennummerService.next());
    }
}
