package com.spirittesting.academy.web.rest.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.spirittesting.academy.domain.Euro;

import java.io.IOException;

public class EuroDeserializer extends StdDeserializer<Euro> {

    public EuroDeserializer() {
        this(null);
    }

    public EuroDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Euro deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String betrag = node.elements().next().asText();
        Euro euro = new Euro(betrag);
        return euro;
    }

}
