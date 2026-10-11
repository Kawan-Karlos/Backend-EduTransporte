package com.example.edutransporte.model.converter;

import com.example.edutransporte.model.enums.TipoUsuario;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Locale;

@Converter
public class TipoUsuarioConverter implements AttributeConverter<TipoUsuario, String> {

    @Override
    public String convertToDatabaseColumn(TipoUsuario attribute) {
        return attribute == null ? null : attribute.name().toLowerCase(Locale.ROOT);
    }

    @Override
    public TipoUsuario convertToEntityAttribute(String dbData) {
        return dbData == null ? null : TipoUsuario.valueOf(dbData.toUpperCase(Locale.ROOT));
    }
}
