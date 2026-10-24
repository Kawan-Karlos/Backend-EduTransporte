package com.example.edutransporte.service;

import com.example.edutransporte.DTOs.LoginDTO;
import com.example.edutransporte.DTOs.MotoristaRequest;
import com.example.edutransporte.DTOs.MotoristaResponse;
import com.example.edutransporte.model.Cidade;
import com.example.edutransporte.model.Motorista;
import com.example.edutransporte.repository.CidadeRepository;
import com.example.edutransporte.repository.MotoristaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class MotoristaService {

    @Autowired
    private final MotoristaRepository motoristaRepository;
    @Autowired
    private final CidadeRepository cidadeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

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

        String senhaCriptografada = passwordEncoder.encode(r.senha());

        Motorista m = new Motorista();
        m.setCpf(r.cpf());
        m.setNomeCompleto(r.nomeCompleto());
        m.setEmail(r.email());
        m.setNumeroCelular(r.numeroCelular());
        m.setSenha(senhaCriptografada);
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

    public boolean login(LoginDTO login){
        Motorista motorista = motoristaRepository.findByCpf(login.cpf()).orElseThrow(() -> new IllegalArgumentException("Motorista não encontrado"));

        if (motorista == null){
            return false;
        }

        return passwordEncoder.matches(login.senha(), motorista.getSenha());

    }
}