package com.example.edutransporte.DTOs;

import com.example.edutransporte.model.enums.TipoUsuario;

public record MensagemRequest(
        String texto,
        String remetenteCpf,
        TipoUsuario remetenteTipo
) {
}