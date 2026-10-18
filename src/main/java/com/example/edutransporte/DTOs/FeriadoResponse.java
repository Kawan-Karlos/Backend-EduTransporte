package com.example.edutransporte.DTOs;

import java.time.LocalDate;

public record FeriadoResponse(LocalDate data, String nome, String descricao) {
}