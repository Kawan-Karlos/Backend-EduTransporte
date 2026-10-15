package com.example.edutransporte.DTOs;

public record MotoristaRequest(
        String cpf,
        String nomeCompleto,
        String email,
        String numeroCelular,
        String senha,
        String carteiraConducao,
        Integer cidadeId
) {}