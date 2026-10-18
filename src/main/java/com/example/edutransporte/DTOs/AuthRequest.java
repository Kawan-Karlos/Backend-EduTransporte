package com.example.edutransporte.DTOs;

public record AuthRequest(
        String cpf,
        String senha
) {
}