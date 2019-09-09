package com.spirittesting.academy.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EuroTest {

    @Test
    void defaultConstructor() {
        assertEquals("EUR 0.00", new Euro().toString());
    }

    @Test
    void fromEuroString() {
        assertEquals("EUR 17.25", new Euro("EUR 17.25").toString());
    }

    @Test void fromPlainString() {
        assertEquals("EUR 17.25", new Euro("17.25").toString());
    }

    @Test void fromIntegers() {
        assertEquals("EUR 17.25", new Euro(17,25).toString());
    }

}
