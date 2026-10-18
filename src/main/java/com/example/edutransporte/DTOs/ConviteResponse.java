package com.example.edutransporte.DTOs;

import com.example.edutransporte.model.enums.TipoUsuario;

import java.time.LocalDateTime;

public record ConviteResponse(
        String codigo,
        Boolean usado,
        LocalDateTime criadoEm,
        LocalDateTime expiraEm,
        TipoUsuario usuarioTipo
) {
}