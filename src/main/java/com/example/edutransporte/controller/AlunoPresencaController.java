package com.example.edutransporte.controller;

import com.example.edutransporte.DTOs.PresencaResponse;
import com.example.edutransporte.service.PresencaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/alunos/{alunoCpf}/presencas")
public class AlunoPresencaController {

    private final PresencaService presencaService;

    public AlunoPresencaController(PresencaService presencaService) {
        this.presencaService = presencaService;
    }

    // Histórico de presenças de um aluno
    @GetMapping
    public List<PresencaResponse> listarPorAluno(@PathVariable String alunoCpf) {
        return presencaService.listarPorAluno(alunoCpf);
    }
}