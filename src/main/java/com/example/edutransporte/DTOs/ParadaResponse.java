package com.example.edutransporte.DTOs;

import java.math.BigDecimal;

public record ParadaResponse(
        Integer ordem,
        String nome,
        BigDecimal latitude,
        BigDecimal longitude
) {}