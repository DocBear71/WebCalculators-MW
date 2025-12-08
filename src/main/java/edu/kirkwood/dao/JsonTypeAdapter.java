package edu.kirkwood.dao;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class JsonTypeAdapter {
    /**
     * Used by Gson when a date formatted line "1970-01-01"
     */
    public static class LocalDateDeserializer implements JsonDeserializer<LocalDate> {

        @Override
        public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            try {
                return LocalDate.parse(json.getAsString());
            } catch (DateTimeParseException e) {
                return null;
            }
        }
    }


}
