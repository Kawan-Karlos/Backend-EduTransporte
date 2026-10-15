package com.example.edutransporte.DTOs;

import com.example.edutransporte.model.enums.TipoViagem;
import java.time.LocalDate;
import java.time.LocalTime;

public record ViagemResponse(
        Integer id,
        String nomeRota,
        TipoViagem tipo,
        LocalDate data,
        LocalTime horario,
        String motoristaCpf,
        String motoristaNome,
        String veiculoPlaca,
        String veiculoModelo
) {}