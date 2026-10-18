package com.example.edutransporte.service;

import com.example.edutransporte.DTOs.PresencaRequest;
import com.example.edutransporte.DTOs.PresencaResponse;
import com.example.edutransporte.model.Aluno;
import com.example.edutransporte.model.Presenca;
import com.example.edutransporte.model.Viagem;
import com.example.edutransporte.repository.AlunoRepository;
import com.example.edutransporte.repository.PresencaRepository;
import com.example.edutransporte.repository.ViagemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PresencaService {

    private final PresencaRepository presencaRepository;
    private final AlunoRepository alunoRepository;
    private final ViagemRepository viagemRepository;

    public PresencaService(PresencaRepository presencaRepository, AlunoRepository alunoRepository,
                           ViagemRepository viagemRepository) {
        this.presencaRepository = presencaRepository;
        this.alunoRepository = alunoRepository;
        this.viagemRepository = viagemRepository;
    }

    @Transactional(readOnly = true)
    public List<PresencaResponse> listarPorViagem(Integer viagemId) {
        return presencaRepository.findByIdViagemId(viagemId).stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<PresencaResponse> listarPorAluno(String alunoCpf) {
        return presencaRepository.findByIdAlunoCpf(alunoCpf).stream().map(this::toResponse).toList();
    }

    /** Confirma a presença; se já existir, só atualiza o campo "presente". */
    @Transactional
    public PresencaResponse registrar(Integer viagemId, PresencaRequest r) {
        Presenca p = presencaRepository.findByIdAlunoCpfAndIdViagemId(r.alunoCpf(), viagemId)
                .orElseGet(() -> {
                    Aluno aluno = alunoRepository.findById(r.alunoCpf())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aluno não encontrado"));
                    Viagem viagem = viagemRepository.findById(viagemId)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Viagem não encontrada"));
                    Presenca nova = new Presenca();
                    nova.getId().setAlunoCpf(aluno.getCpf());
                    nova.getId().setViagemId(viagemId);
                    nova.setAluno(aluno);
                    nova.setViagem(viagem);
                    return nova;
                });

        if (r.presente() != null) {
            p.setPresente(r.presente());
        }
        return toResponse(presencaRepository.save(p));
    }

    @Transactional
    public void deletar(Integer viagemId, String alunoCpf) {
        Presenca p = presencaRepository.findByIdAlunoCpfAndIdViagemId(alunoCpf, viagemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Presença não encontrada"));
        presencaRepository.delete(p);
    }

    private PresencaResponse toResponse(Presenca p) {
        return new PresencaResponse(p.getAluno().getCpf(), p.getAluno().getNomeCompleto(),
                p.getViagem().getId(), p.getPresente(), p.getConfirmadoEm());
    }
}