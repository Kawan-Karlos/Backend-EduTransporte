package com.example.edutransporte.service;

import com.example.edutransporte.DTOs.ParadaRequest;
import com.example.edutransporte.DTOs.ParadaResponse;
import com.example.edutransporte.model.Parada;
import com.example.edutransporte.model.Viagem;
import com.example.edutransporte.repository.ParadaRepository;
import com.example.edutransporte.repository.ViagemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ParadaService {

    private final ParadaRepository paradaRepository;
    private final ViagemRepository viagemRepository;

    public ParadaService(ParadaRepository paradaRepository, ViagemRepository viagemRepository) {
        this.paradaRepository = paradaRepository;
        this.viagemRepository = viagemRepository;
    }

    @Transactional(readOnly = true)
    public List<ParadaResponse> listarPorViagem(Integer viagemId) {
        return paradaRepository.findByIdViagemIdOrderByIdOrdem(viagemId)
                .stream().map(this::toResponse).toList();
    }

    @Transactional
    public ParadaResponse criar(Integer viagemId, ParadaRequest r) {
        if (paradaRepository.existsByIdViagemIdAndIdOrdem(viagemId, r.ordem())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Já existe parada com essa ordem na viagem");
        }
        Viagem viagem = viagemRepository.findById(viagemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Viagem não encontrada"));

        Parada p = new Parada();
        p.getId().setViagemId(viagemId);
        p.getId().setOrdem(r.ordem());
        p.setViagem(viagem);
        p.setNome(r.nome());
        p.setLatitude(r.latitude());
        p.setLongitude(r.longitude());
        return toResponse(paradaRepository.save(p));
    }

    @Transactional
    public ParadaResponse atualizar(Integer viagemId, Integer ordem, ParadaRequest r) {
        Parada p = paradaRepository.findByIdViagemIdAndIdOrdem(viagemId, ordem)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parada não encontrada"));
        p.setNome(r.nome());
        p.setLatitude(r.latitude());
        p.setLongitude(r.longitude());
        return toResponse(paradaRepository.save(p));
    }

    @Transactional
    public void deletar(Integer viagemId, Integer ordem) {
        Parada p = paradaRepository.findByIdViagemIdAndIdOrdem(viagemId, ordem)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parada não encontrada"));
        paradaRepository.delete(p);
    }

    private ParadaResponse toResponse(Parada p) {
        return new ParadaResponse(p.getId().getOrdem(), p.getNome(), p.getLatitude(), p.getLongitude());
    }
}