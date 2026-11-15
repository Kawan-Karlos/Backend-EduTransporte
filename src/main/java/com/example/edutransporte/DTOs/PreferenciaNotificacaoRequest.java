package com.example.edutransporte.DTOs;

// Campos nulos são ignorados: permite alterar só uma chave por vez
public record PreferenciaNotificacaoRequest(
        Boolean avisosImportantes,
        Boolean presenca,
        Boolean mensagens,
        Boolean rotas
) {}
