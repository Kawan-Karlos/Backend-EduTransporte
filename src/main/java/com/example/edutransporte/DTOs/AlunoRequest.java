package com.example.edutransporte.DTOs;

public record AlunoRequest(
        String cpf,
        String nomeCompleto,
        String numeroCelular,
        String senha,
        Integer cidadeId,
        Integer faculdadeId
) {}