package com.example.edutransporte.controller;

import com.example.edutransporte.DTOs.PreferenciaNotificacaoRequest;
import com.example.edutransporte.model.PreferenciaNotificacao;
import com.example.edutransporte.service.PreferenciaNotificacaoService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Preferências de notificação do usuário logado (aluno ou motorista).
 * GET  /api/usuarios/me/notificacoes
 * PUT  /api/usuarios/me/notificacoes   (também aceita PATCH)
 * Corpo: {"avisosImportantes":true,"presenca":false,"mensagens":true,"rotas":true}
 */
@RestController
@RequestMapping("/api/usuarios/me/notificacoes")
public class NotificacaoController {

    private final PreferenciaNotificacaoService service;

    public NotificacaoController(PreferenciaNotificacaoService service) {
        this.service = service;
    }

    @GetMapping
    public PreferenciaNotificacao buscar(Authentication auth) {
        return service.buscar(auth.getName());
    }

    @RequestMapping(method = {RequestMethod.PUT, RequestMethod.PATCH})
    public PreferenciaNotificacao atualizar(Authentication auth,
                                            @RequestBody PreferenciaNotificacaoRequest request) {
        return service.atualizar(auth.getName(), request);
    }
}
