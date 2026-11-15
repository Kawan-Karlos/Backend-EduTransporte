package com.example.edutransporte.service;

import com.example.edutransporte.DTOs.PreferenciaNotificacaoRequest;
import com.example.edutransporte.model.PreferenciaNotificacao;
import com.example.edutransporte.repository.PreferenciaNotificacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PreferenciaNotificacaoService {

    private final PreferenciaNotificacaoRepository repository;

    public PreferenciaNotificacaoService(PreferenciaNotificacaoRepository repository) {
        this.repository = repository;
    }

    /** Retorna as preferências; se o usuário nunca salvou, tudo vem ligado. */
    public PreferenciaNotificacao buscar(String cpf) {
        return repository.findById(cpf).orElseGet(() -> padrao(cpf));
    }

    @Transactional
    public PreferenciaNotificacao atualizar(String cpf, PreferenciaNotificacaoRequest req) {
        PreferenciaNotificacao p = buscar(cpf);
        if (req.avisosImportantes() != null) p.setAvisosImportantes(req.avisosImportantes());
        if (req.presenca() != null) p.setPresenca(req.presenca());
        if (req.mensagens() != null) p.setMensagens(req.mensagens());
        if (req.rotas() != null) p.setRotas(req.rotas());
        return repository.save(p);
    }

    private PreferenciaNotificacao padrao(String cpf) {
        return new PreferenciaNotificacao(cpf, true, true, true, true);
    }
}
