package com.example.edutransporte.service;

import com.example.edutransporte.DTOs.MensagemRequest;
import com.example.edutransporte.DTOs.MensagemResponse;
import com.example.edutransporte.model.Mensagem;
import com.example.edutransporte.model.Viagem;
import com.example.edutransporte.repository.MensagemRepository;
import com.example.edutransporte.repository.ViagemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MensagemService {

    private final MensagemRepository mensagemRepository;
    private final ViagemRepository viagemRepository;

    public MensagemService(MensagemRepository mensagemRepository, ViagemRepository viagemRepository) {
        this.mensagemRepository = mensagemRepository;
        this.viagemRepository = viagemRepository;
    }

    @Transactional(readOnly = true)
    public List<MensagemResponse> listarPorViagem(Integer viagemId) {
        return mensagemRepository.findByViagem_IdAndDeletadoFalseOrderByCreatedAtAsc(viagemId)
                .stream().map(this::toResponse).toList();
    }

    @Transactional
    public MensagemResponse enviar(Integer viagemId, MensagemRequest r) {
        if (r.texto() == null || r.texto().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mensagem vazia");
        }
        Viagem viagem = viagemRepository.findById(viagemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Viagem não encontrada"));

        Mensagem m = new Mensagem();
        m.setTexto(r.texto());
        m.setRemetenteCpf(r.remetenteCpf());
        m.setRemetenteTipo(r.remetenteTipo());
        m.setViagem(viagem);
        return toResponse(mensagemRepository.save(m));
    }

    /** Exclusão lógica: marca como deletada, não remove do banco. */
    @Transactional
    public void deletar(Integer id) {
        Mensagem m = mensagemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mensagem não encontrada"));
        m.setDeletado(true);
        mensagemRepository.save(m);
    }

    private MensagemResponse toResponse(Mensagem m) {
        return new MensagemResponse(m.getId(), m.getTexto(), m.getRemetenteTipo(),
                m.getViagem().getId(), m.getCreatedAt());
    }
}