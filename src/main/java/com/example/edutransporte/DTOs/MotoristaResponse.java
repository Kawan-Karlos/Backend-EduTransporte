package com.example.edutransporte.DTOs;

public record MotoristaResponse(
        String cpf,
        String nomeCompleto,
        String email,
        String numeroCelular,
        String carteiraConducao,
        Integer cidadeId,
        String cidadeNome
) {}