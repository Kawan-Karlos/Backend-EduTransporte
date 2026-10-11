package com.example.edutransporte.model.converter;

import com.example.edutransporte.model.enums.TipoViagem;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Locale;

@Converter
public class TipoViagemConverter implements AttributeConverter<TipoViagem, String> {

    @Override
    public String convertToDatabaseColumn(TipoViagem attribute) {
        return attribute == null ? null : attribute.name().toLowerCase(Locale.ROOT);
    }

    @Override
    public TipoViagem convertToEntityAttribute(String dbData) {
        return dbData == null ? null : TipoViagem.valueOf(dbData.toUpperCase(Locale.ROOT));
    }
}
