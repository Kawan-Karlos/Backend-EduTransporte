package com.example.edutransporte.DTOs;

import java.time.LocalDate;

public record MotoristaVeiculoResponse(
        String veiculoPlaca,
        LocalDate dataDesatribuicao,
        boolean ativo
) {
}