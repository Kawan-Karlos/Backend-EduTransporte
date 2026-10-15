package com.example.edutransporte.service;

import com.example.edutransporte.DTOs.MotoristaRequest;
import com.example.edutransporte.DTOs.MotoristaResponse;
import com.example.edutransporte.model.Cidade;
import com.example.edutransporte.model.Motorista;
import com.example.edutransporte.repository.CidadeRepository;
import com.example.edutransporte.repository.MotoristaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class MotoristaService {

    private final MotoristaRepository motoristaRepository;
    private final CidadeRepository cidadeRepository;

    public MotoristaService(MotoristaRepository motoristaRepository, CidadeRepository cidadeRepository) {
        this.motoristaRepository = motoristaRepository;
        this.cidadeRepository = cidadeRepository;
    }

    @Transactional(readOnly = true)
    public List<MotoristaResponse> listarTodos() {
        return motoristaRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public Optional<MotoristaResponse> buscarPorCpf(String cpf) {
        return motoristaRepository.findById(cpf).map(this::toResponse);
    }

    @Transactional
    public MotoristaResponse criar(MotoristaRequest r) {
        if (motoristaRepository.existsById(r.cpf())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF já cadastrado");
        }
        if (motoristaRepository.existsByEmail(r.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já cadastrado");
        }
        Cidade cidade = cidadeRepository.findById(r.cidadeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cidade não encontrada"));

        Motorista m = new Motorista();
        m.setCpf(r.cpf());
        m.setNomeCompleto(r.nomeCompleto());
        m.setEmail(r.email());
        m.setNumeroCelular(r.numeroCelular());
        m.setSenha(r.senha());
        m.setCarteiraConducao(r.carteiraConducao());
        m.setCidade(cidade);
        return toResponse(motoristaRepository.save(m));
    }

    public void deletar(String cpf) {
        motoristaRepository.deleteById(cpf);
    }

    private MotoristaResponse toResponse(Motorista m) {
        return new MotoristaResponse(m.getCpf(), m.getNomeCompleto(), m.getEmail(), m.getNumeroCelular(),
                m.getCarteiraConducao(), m.getCidade().getId(), m.getCidade().getNome());
    }
}