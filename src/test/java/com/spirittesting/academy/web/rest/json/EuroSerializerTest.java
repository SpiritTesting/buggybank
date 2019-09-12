package com.spirittesting.academy.web.rest.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.spirittesting.academy.domain.Euro;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EuroSerializerTest {

    @Test
    void serialisiertZero() throws IOException {
        assertEquals("\"EUR 0.00\"", serialize(Euro.ZERO));
    }

    @Test
    void serialisiertPositiv() throws IOException {
        assertEquals("\"EUR 100.00\"", serialize(new Euro(100)));
    }

    @Test
    void serialisiertNegativ() throws IOException {
        assertEquals("\"EUR -100.00\"", serialize(new Euro(-100)));
    }

    @Test
    void serialisiertNachkommastellen() throws IOException {
        assertEquals("\"EUR 100.50\"", serialize(new Euro(100, 50)));
    }

    @Test
    void serialisiertNull() throws IOException {
        assertThrows(NullPointerException.class, () -> serialize(null));
    }

    private String serialize(Euro euro) throws IOException {
        Writer jsonWriter = new StringWriter();
        JsonGenerator generator = new JsonFactory().createGenerator(jsonWriter);
        SerializerProvider serializerProvider = new ObjectMapper().getSerializerProvider();

        new EuroSerializer().serialize(euro, generator, serializerProvider);
        generator.flush();
        return jsonWriter.toString();
    }

}
