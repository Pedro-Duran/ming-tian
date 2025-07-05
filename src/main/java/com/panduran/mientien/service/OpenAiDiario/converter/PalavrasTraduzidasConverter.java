package com.panduran.mientien.service.OpenAiDiario.converter;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.Map;

@Converter
public class PalavrasTraduzidasConverter implements AttributeConverter<Map<String, String>, String> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, String> map) {
        try {
            return mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Erro ao serializar palavrasTraduzidas", e);
        }
    }

    @Override
    public Map<String, String> convertToEntityAttribute(String json) {
        try {
            return mapper.readValue(json, new TypeReference<>() {});
        } catch (IOException e) {
            throw new IllegalArgumentException("Erro ao desserializar palavrasTraduzidas", e);
        }
    }
}