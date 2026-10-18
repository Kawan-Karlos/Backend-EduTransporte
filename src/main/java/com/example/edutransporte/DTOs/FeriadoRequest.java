package com.example.edutransporte.DTOs;

import java.time.LocalDate;

public record FeriadoRequest(LocalDate data, String nome, String descricao) {
}