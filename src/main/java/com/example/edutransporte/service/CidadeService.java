package com.example.edutransporte.service;

import com.example.edutransporte.DTOs.CidadeRequest;
import com.example.edutransporte.DTOs.CidadeResponse;
import com.example.edutransporte.model.Cidade;
import com.example.edutransporte.repository.CidadeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CidadeService {

    private final CidadeRepository cidadeRepository;

    public CidadeService(CidadeRepository cidadeRepository) {
        this.cidadeRepository = cidadeRepository;
    }

    @Transactional(readOnly = true)
    public List<CidadeResponse> listarTodos() {
        return cidadeRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public Optional<CidadeResponse> buscarPorId(Integer id) {
        return cidadeRepository.findById(id).map(this::toResponse);
    }

    @Transactional
    public CidadeResponse criar(CidadeRequest r) {
        Cidade c = new Cidade();
        c.setNome(r.nome());
        return toResponse(cidadeRepository.save(c));
    }

    @Transactional
    public CidadeResponse atualizar(Integer id, CidadeRequest r) {
        Cidade c = cidadeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cidade não encontrada"));
        c.setNome(r.nome());
        return toResponse(cidadeRepository.save(c));
    }

    @Transactional
    public void deletar(Integer id) {
        if (!cidadeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cidade não encontrada");
        }
        cidadeRepository.deleteById(id);
    }

    private CidadeResponse toResponse(Cidade c) {
        return new CidadeResponse(c.getId(), c.getNome());
    }
}