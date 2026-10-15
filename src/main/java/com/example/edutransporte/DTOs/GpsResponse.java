package com.example.edutransporte.DTOs;

import java.math.BigDecimal;

public record GpsResponse(
        Integer id,
        Integer viagemId,
        BigDecimal latitude,
        BigDecimal longitude,
        Long timestamp
) {}