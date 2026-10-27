package com.example.edutransporte.controller;

import com.example.edutransporte.DTOs.PresencaRequest;
import com.example.edutransporte.DTOs.PresencaResponse;
import com.example.edutransporte.service.PresencaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/viagens/{viagemId}/presencas")
public class PresencaController {

    private final PresencaService presencaService;

    public PresencaController(PresencaService presencaService) {
        this.presencaService = presencaService;
    }

    // Aluno marca presença na viagem (se já existir, só atualiza o campo "presente")
    @PostMapping
    public ResponseEntity<PresencaResponse> marcar(@PathVariable Integer viagemId,
                                                   @RequestBody PresencaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(presencaService.registrar(viagemId, request));
    }

    // Aluno desmarca presença (remove o nome da lista)
    @DeleteMapping("/{alunoCpf}")
    public ResponseEntity<Void> desmarcar(@PathVariable Integer viagemId,
                                          @PathVariable String alunoCpf) {
        presencaService.deletar(viagemId, alunoCpf);
        return ResponseEntity.noContent().build();
    }

    // Motorista / Adm consulta a lista de alunos da viagem
    @GetMapping
    public List<PresencaResponse> listarPorViagem(@PathVariable Integer viagemId) {
        return presencaService.listarPorViagem(viagemId);
    }
}