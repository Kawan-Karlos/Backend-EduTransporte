package com.example.edutransporte.DTOs;

import com.example.edutransporte.model.enums.TipoUsuario;

public record AuthResponse(
        String token,
        TipoUsuario tipo
) {
}