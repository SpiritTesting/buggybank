package com.spirittesting.academy.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KontonummerServiceTest {

    @Test
    void next() {
        KontonummerService kontonummerService = new KontonummerService(10, "%d");
        assertEquals("10", kontonummerService.next());
        assertEquals("11", kontonummerService.next());
    }
}
