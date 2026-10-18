package com.example.edutransporte.controller;

import com.example.edutransporte.DTOs.AlunoRequest;
import com.example.edutransporte.DTOs.AlunoResponse;
import com.example.edutransporte.service.AlunoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService service;

    public AlunoController(AlunoService service) {
        this.service = service;
    }

    /** Cadastro público (liberado no SecurityConfig). */
    @PostMapping
    public ResponseEntity<AlunoResponse> criar(@RequestBody AlunoRequest r) {
        return ResponseEntity.status(201).body(service.criar(r));
    }

    @GetMapping
    public List<AlunoResponse> listar() {
        return service.listarTodos();
    }

    /** Dados do aluno logado, usando o CPF do token. */
    @GetMapping("/me")
    public ResponseEntity<AlunoResponse> meusDados(@AuthenticationPrincipal String cpf) {
        return service.buscarPorCpf(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deletarMinhaConta(@AuthenticationPrincipal String cpf) {
        service.deletar(cpf);
        return ResponseEntity.noContent().build();
    }
}