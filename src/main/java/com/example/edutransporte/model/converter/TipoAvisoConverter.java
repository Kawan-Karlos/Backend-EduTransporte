package com.example.edutransporte.model.converter;

import com.example.edutransporte.model.enums.TipoAviso;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Locale;

@Converter
public class TipoAvisoConverter implements AttributeConverter<TipoAviso, String> {

    @Override
    public String convertToDatabaseColumn(TipoAviso attribute) {
        return attribute == null ? null : attribute.name().toLowerCase(Locale.ROOT);
    }

    @Override
    public TipoAviso convertToEntityAttribute(String dbData) {
        return dbData == null ? null : TipoAviso.valueOf(dbData.toUpperCase(Locale.ROOT));
    }
}
