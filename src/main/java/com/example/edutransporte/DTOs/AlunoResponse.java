package com.example.edutransporte.DTOs;

public record AlunoResponse(
        String cpf,
        String nomeCompleto,
        String numeroCelular,
        Integer cidadeId,
        String cidadeNome,
        Integer faculdadeId,
        String faculdadeNome
) {}