package com.capa.boostify.user.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import io.micrometer.common.util.StringUtils;

import java.io.IOException;

public class DivisionEnumDeserializer extends JsonDeserializer<Division> {
    @Override
    public Division deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String value = jsonParser.readValueAs(String.class);
        if (StringUtils.isBlank(value)) {
            return Division.NONE;
        }
        value = value.toUpperCase();
        if (!Division.isValidEnumValue(value)) {
            return Division.NONE;
        }

        return Division.valueOf(value);
    }
}
