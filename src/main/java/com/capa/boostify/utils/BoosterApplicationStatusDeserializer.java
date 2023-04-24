package com.capa.boostify.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import io.micrometer.common.util.StringUtils;

import java.io.IOException;

public class BoosterApplicationStatusDeserializer extends JsonDeserializer<BoosterApplicationStatus> {
    @Override
    public BoosterApplicationStatus deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String value = jsonParser.readValueAs(String.class);
        if (StringUtils.isBlank(value)) {
            return BoosterApplicationStatus.NONE;
        }
        value = value.toUpperCase();
        if (!BoosterApplicationStatus.isValidEnumValue(value)) {
            return BoosterApplicationStatus.NONE;
        }

        return BoosterApplicationStatus.valueOf(value);
    }
}
