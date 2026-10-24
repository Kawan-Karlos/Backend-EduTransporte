package com.example.edutransporte.controller;

import com.example.edutransporte.DTOs.*;
import com.example.edutransporte.service.ViagemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Rotas claras para iniciar viagem, encerrar viagem e buscar a viagem ativa do dia ( TipoViagem : Ida/Volta).

@RestController
@RequestMapping("/api/viagens")
public class ViagemController {

    private final ViagemService viagemService;

    public ViagemController(ViagemService viagemService) {
        this.viagemService = viagemService;
    }

    @GetMapping
    public List<ViagemResponse> listar() {
        return viagemService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViagemResponse> buscar(@PathVariable Integer id) {
        return viagemService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ViagemResponse> criar(@RequestBody ViagemRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(viagemService.criar(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        viagemService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/paradas")
    public List<ParadaResponse> listarParadas(@PathVariable Integer id) {
        return viagemService.listarParadas(id);
    }

    @PostMapping("/{id}/paradas")
    public ResponseEntity<ParadaResponse> adicionarParada(@PathVariable Integer id,
                                                          @RequestBody ParadaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(viagemService.adicionarParada(id, request));
    }

    @GetMapping("/{id}/presencas")
    public List<PresencaResponse> listarPresencas(@PathVariable Integer id) {
        return viagemService.listarPresencas(id);
    }

    @PostMapping("/{id}/presencas")
    public ResponseEntity<PresencaResponse> registrarPresenca(@PathVariable Integer id,
                                                              @RequestBody PresencaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(viagemService.registrarPresenca(id, request));
    }
}