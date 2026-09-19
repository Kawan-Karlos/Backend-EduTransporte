package com.example.edutransporte;

import com.example.edutransporte.model.Aviso;
import com.example.edutransporte.service.AvisoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avisos")
public class AvisoController {

    private final AvisoService avisoService;

    // Injeção de dependência via construtor
    public AvisoController(AvisoService avisoService) {
        this.avisoService = avisoService;
    }

    // Listar todos os avisos (GET)
    @GetMapping
    public List<Aviso> listarTodos() {
        return avisoService.listarTodos();
    }

    // Buscar aviso por ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Aviso> buscarPorId(@PathVariable Long id) {
        return avisoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar um novo aviso (POST)
    @PostMapping
    public Aviso salvar(@RequestBody Aviso aviso) {
        return avisoService.salvar(aviso);
    }

    // Apagar um aviso por ID (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        avisoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

