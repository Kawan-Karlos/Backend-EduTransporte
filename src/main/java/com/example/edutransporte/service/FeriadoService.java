package com.example.edutransporte.service;

import com.example.edutransporte.DTOs.FeriadoRequest;
import com.example.edutransporte.DTOs.FeriadoResponse;
import com.example.edutransporte.model.Feriado;
import com.example.edutransporte.repository.FeriadoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FeriadoService {

    private final FeriadoRepository feriadoRepository;

    public FeriadoService(FeriadoRepository feriadoRepository) {
        this.feriadoRepository = feriadoRepository;
    }

    @Transactional(readOnly = true)
    public List<FeriadoResponse> listarTodos() {
        return feriadoRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public Optional<FeriadoResponse> buscarPorData(LocalDate data) {
        return feriadoRepository.findById(data).map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public boolean ehFeriado(LocalDate data) {
        return feriadoRepository.existsById(data);
    }

    @Transactional
    public FeriadoResponse criar(FeriadoRequest r) {
        if (feriadoRepository.existsById(r.data())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe feriado nessa data");
        }
        Feriado f = new Feriado();
        f.setData(r.data());
        f.setNome(r.nome());
        f.setDescricao(r.descricao());
        return toResponse(feriadoRepository.save(f));
    }

    @Transactional
    public FeriadoResponse atualizar(LocalDate data, FeriadoRequest r) {
        Feriado f = feriadoRepository.findById(data)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Feriado não encontrado"));
        f.setNome(r.nome());
        f.setDescricao(r.descricao());
        return toResponse(feriadoRepository.save(f));
    }

    @Transactional
    public void deletar(LocalDate data) {
        if (!feriadoRepository.existsById(data)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Feriado não encontrado");
        }
        feriadoRepository.deleteById(data);
    }

    private FeriadoResponse toResponse(Feriado f) {
        return new FeriadoResponse(f.getData(), f.getNome(), f.getDescricao());
    }
}