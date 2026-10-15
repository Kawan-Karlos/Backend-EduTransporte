package com.example.edutransporte.service;

import com.example.edutransporte.DTOs.GpsRequest;
import com.example.edutransporte.DTOs.GpsResponse;
import com.example.edutransporte.model.GpsPosicao;
import com.example.edutransporte.model.Viagem;
import com.example.edutransporte.repository.GpsPosicaoRepository;
import com.example.edutransporte.repository.ViagemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class GpsService {

    private final GpsPosicaoRepository gpsRepository;
    private final ViagemRepository viagemRepository;

    public GpsService(GpsPosicaoRepository gpsRepository, ViagemRepository viagemRepository) {
        this.gpsRepository = gpsRepository;
        this.viagemRepository = viagemRepository;
    }

    @Transactional
    public GpsResponse registrar(Integer viagemId, GpsRequest r) {
        Viagem viagem = viagemRepository.findById(viagemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Viagem não encontrada"));
        GpsPosicao g = new GpsPosicao();
        g.setViagem(viagem);
        g.setLatitude(r.latitude());
        g.setLongitude(r.longitude());
        g.setTimestamp(r.timestamp());
        return toResponse(viagemId, gpsRepository.save(g));
    }

    @Transactional(readOnly = true)
    public Optional<GpsResponse> ultima(Integer viagemId) {
        return gpsRepository.findFirstByViagemIdOrderByTimestampDesc(viagemId)
                .map(g -> toResponse(viagemId, g));
    }

    @Transactional(readOnly = true)
    public List<GpsResponse> historico(Integer viagemId) {
        return gpsRepository.findByViagemIdOrderByTimestampAsc(viagemId).stream()
                .map(g -> toResponse(viagemId, g)).toList();
    }

    private GpsResponse toResponse(Integer viagemId, GpsPosicao g) {
        return new GpsResponse(g.getId(), viagemId, g.getLatitude(), g.getLongitude(), g.getTimestamp());
    }
}