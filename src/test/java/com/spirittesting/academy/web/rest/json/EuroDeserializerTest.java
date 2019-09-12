package com.spirittesting.academy.web.rest.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spirittesting.academy.domain.Euro;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class EuroDeserializerTest {

    private ObjectMapper objectMapper = new ObjectMapper();
    private EuroDeserializer euroDeserializer = new EuroDeserializer();

    @Test
    void positiverWert() throws IOException {
        assertEquals(new Euro(100), deserialize("{\"betrag\":\"EUR 100.00\"}"));
    }

    @Test
    void negativerWert() throws IOException {
        assertEquals(new Euro(-100), deserialize("{\"betrag\":\"EUR -100.00\"}"));
    }

    @Test
    void kurzerWert() throws IOException {
        assertEquals(new Euro(100), deserialize("{\"betrag\":\"EUR 100\"}"));
    }

    @Test
    void langerWert() throws IOException {
        assertEquals(new Euro(100), deserialize("{\"betrag\":\"EUR 100.001\"}"));
    }

    @Test
    void nullWert() throws IOException {
        assertEquals(new Euro(0), deserialize("{\"betrag\":\"EUR -0\"}"));
    }


    private Euro deserialize(String json) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
        JsonParser jsonParser = objectMapper.getFactory().createParser(inputStream);
        DeserializationContext deserializationContext = objectMapper.getDeserializationContext();
        jsonParser.nextToken();
        return euroDeserializer.deserialize(jsonParser, deserializationContext);
    }

}
