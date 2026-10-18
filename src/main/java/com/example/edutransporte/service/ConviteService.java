package com.example.edutransporte.service;

import com.example.edutransporte.DTOs.ConviteRequest;
import com.example.edutransporte.DTOs.ConviteResponse;
import com.example.edutransporte.model.Convite;
import com.example.edutransporte.repository.ConviteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ConviteService {

    private final ConviteRepository conviteRepository;

    public ConviteService(ConviteRepository conviteRepository) {
        this.conviteRepository = conviteRepository;
    }

    @Transactional(readOnly = true)
    public List<ConviteResponse> listarTodos() {
        return conviteRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional
    public ConviteResponse criar(ConviteRequest r) {
        if (r.expiraEm() == null || r.expiraEm().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Data de expiração inválida");
        }
        Convite c = new Convite();
        c.setCodigo(UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase());
        c.setExpiraEm(r.expiraEm());
        c.setUsuarioTipo(r.usuarioTipo());
        return toResponse(conviteRepository.save(c));
    }

    /** Valida o código sem consumir (útil na tela de cadastro). */
    @Transactional(readOnly = true)
    public ConviteResponse validar(String codigo) {
        return toResponse(buscarValido(codigo));
    }

    /** Consome o convite e vincula ao CPF de quem se cadastrou. */
    @Transactional
    public ConviteResponse usar(String codigo, String usuarioCpf) {
        Convite c = buscarValido(codigo);
        c.setUsado(true);
        c.setUsuarioCpf(usuarioCpf);
        return toResponse(conviteRepository.save(c));
    }

    @Transactional
    public void deletar(String codigo) {
        if (!conviteRepository.existsById(codigo)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Convite não encontrado");
        }
        conviteRepository.deleteById(codigo);
    }

    private Convite buscarValido(String codigo) {
        Convite c = conviteRepository.findById(codigo)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Convite não encontrado"));
        if (Boolean.TRUE.equals(c.getUsado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Convite já utilizado");
        }
        if (c.getExpiraEm().isBefore(LocalDateTime.now())) {
            throw new ResponseStatusException(HttpStatus.GONE, "Convite expirado");
        }
        return c;
    }

    private ConviteResponse toResponse(Convite c) {
        return new ConviteResponse(c.getCodigo(), c.getUsado(), c.getCriadoEm(),
                c.getExpiraEm(), c.getUsuarioTipo());
    }
}