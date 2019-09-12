package com.spirittesting.academy.web.rest.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.spirittesting.academy.domain.Euro;

import java.io.IOException;

public class EuroSerializer extends StdSerializer<Euro> {

    public EuroSerializer() {
        this(null);
    }

    public EuroSerializer(Class<Euro> vc) {
        super(vc);
    }

    @Override
    public void serialize(Euro euro, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(euro.toString());
    }


}
