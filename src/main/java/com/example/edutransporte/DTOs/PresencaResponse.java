package com.example.edutransporte.DTOs;

import java.time.LocalDateTime;

public record PresencaResponse(
        String alunoCpf,
        String alunoNome,
        Integer viagemId,
        Boolean presente,
        LocalDateTime confirmadoEm
) {}