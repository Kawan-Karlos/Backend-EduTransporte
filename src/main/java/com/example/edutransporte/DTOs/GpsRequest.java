package com.example.edutransporte.DTOs;

import java.math.BigDecimal;

public record GpsRequest(
        BigDecimal latitude,
        BigDecimal longitude,
        Long timestamp
) {}