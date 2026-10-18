package com.example.edutransporte.service;

import com.example.edutransporte.DTOs.FaculdadeRequest;
import com.example.edutransporte.DTOs.FaculdadeResponse;
import com.example.edutransporte.model.Faculdade;
import com.example.edutransporte.repository.FaculdadeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class FaculdadeService {

    private final FaculdadeRepository faculdadeRepository;

    public FaculdadeService(FaculdadeRepository faculdadeRepository) {
        this.faculdadeRepository = faculdadeRepository;
    }

    @Transactional(readOnly = true)
    public List<FaculdadeResponse> listarTodos() {
        return faculdadeRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public Optional<FaculdadeResponse> buscarPorId(Integer id) {
        return faculdadeRepository.findById(id).map(this::toResponse);
    }

    @Transactional
    public FaculdadeResponse criar(FaculdadeRequest r) {
        Faculdade f = new Faculdade();
        f.setNome(r.nome());
        return toResponse(faculdadeRepository.save(f));
    }

    @Transactional
    public FaculdadeResponse atualizar(Integer id, FaculdadeRequest r) {
        Faculdade f = faculdadeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Faculdade não encontrada"));
        f.setNome(r.nome());
        return toResponse(faculdadeRepository.save(f));
    }

    @Transactional
    public void deletar(Integer id) {
        if (!faculdadeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Faculdade não encontrada");
        }
        faculdadeRepository.deleteById(id);
    }

    private FaculdadeResponse toResponse(Faculdade f) {
        return new FaculdadeResponse(f.getId(), f.getNome());
    }
}