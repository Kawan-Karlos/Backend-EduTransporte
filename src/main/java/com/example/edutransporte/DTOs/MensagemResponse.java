package com.example.edutransporte.DTOs;

import com.example.edutransporte.model.enums.TipoUsuario;

import java.time.LocalDateTime;

public record MensagemResponse(
        Integer id,
        String texto,
        TipoUsuario remetenteTipo,
        Integer viagemId,
        LocalDateTime createdAt
) {
}