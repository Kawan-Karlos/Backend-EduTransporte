package com.example.edutransporte.service;

import com.example.edutransporte.DTOs.AlunoRequest;
import com.example.edutransporte.DTOs.AlunoResponse;
import com.example.edutransporte.model.Aluno;
import com.example.edutransporte.model.Cidade;
import com.example.edutransporte.model.Faculdade;
import com.example.edutransporte.repository.AlunoRepository;
import com.example.edutransporte.repository.CidadeRepository;
import com.example.edutransporte.repository.FaculdadeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final CidadeRepository cidadeRepository;
    private final FaculdadeRepository faculdadeRepository;
    private final PasswordEncoder passwordEncoder;

    public AlunoService(AlunoRepository alunoRepository,
                        CidadeRepository cidadeRepository,
                        FaculdadeRepository faculdadeRepository,
                        PasswordEncoder passwordEncoder) {
        this.alunoRepository = alunoRepository;
        this.cidadeRepository = cidadeRepository;
        this.faculdadeRepository = faculdadeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public List<AlunoResponse> listarTodos() {
        return alunoRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public Optional<AlunoResponse> buscarPorCpf(String cpf) {
        return alunoRepository.findById(cpf).map(this::toResponse);
    }

    @Transactional
    public AlunoResponse criar(AlunoRequest r) {
        if (alunoRepository.existsById(r.cpf())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "CPF já cadastrado");
        }
        Cidade cidade = cidadeRepository.findById(r.cidadeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cidade não encontrada"));
        Faculdade faculdade = faculdadeRepository.findById(r.faculdadeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Faculdade não encontrada"));

        Aluno a = new Aluno();
        a.setCpf(r.cpf());
        a.setNomeCompleto(r.nomeCompleto());
        a.setNumeroCelular(r.numeroCelular());
        a.setSenha(passwordEncoder.encode(r.senha()));
        a.setCidade(cidade);
        a.setFaculdade(faculdade);
        return toResponse(alunoRepository.save(a));
    }

    @Transactional
    public void deletar(String cpf) {
        if (!alunoRepository.existsById(cpf)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado");
        }
        alunoRepository.deleteById(cpf);
    }

    private AlunoResponse toResponse(Aluno a) {
        return new AlunoResponse(a.getCpf(), a.getNomeCompleto(), a.getNumeroCelular(),
                a.getCidade().getId(), a.getCidade().getNome(),
                a.getFaculdade().getId(), a.getFaculdade().getNome());
    }
}