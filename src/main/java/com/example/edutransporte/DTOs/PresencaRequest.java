package com.example.edutransporte.DTOs;

public record PresencaRequest(
        String alunoCpf,
        Boolean presente
) {}