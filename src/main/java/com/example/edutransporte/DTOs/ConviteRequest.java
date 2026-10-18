package com.example.edutransporte.DTOs;

import com.example.edutransporte.model.enums.TipoUsuario;

import java.time.LocalDateTime;

public record ConviteRequest(
        LocalDateTime expiraEm,
        TipoUsuario usuarioTipo
) {
}