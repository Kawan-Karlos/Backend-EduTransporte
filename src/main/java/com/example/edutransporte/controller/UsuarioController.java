package com.example.edutransporte.controller;

import com.example.edutransporte.DTOs.AlunoRequest;
import com.example.edutransporte.DTOs.AlunoResponse;
import com.example.edutransporte.DTOs.MotoristaRequest;
import com.example.edutransporte.DTOs.MotoristaResponse;
import com.example.edutransporte.model.Usuario;
import com.example.edutransporte.service.AlunoService;
import com.example.edutransporte.service.MotoristaService;
import com.example.edutransporte.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// Organizar esse controller

//Gestão de motoristas e vínculo com veículos (MotoristaService e MotoristaVeiculoService).
//Atualizar dados pessoais, trocar senha ("security.tsx") e listar/ativar/desativar "users" no painel admin.
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AlunoService alunoService;
    private final MotoristaService motoristaService;

    public UsuarioController(UsuarioService usuarioService,
                             AlunoService alunoService,
                             MotoristaService motoristaService) {
        this.usuarioService = usuarioService;
        this.alunoService = alunoService;
        this.motoristaService = motoristaService;
    }

    // ---------- "Users" (tb_usuario) ----------
    @GetMapping
    public List<Usuario> listarTodos() {
        return usuarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Usuario salvar(@RequestBody Usuario usuario) {
        return usuarioService.salvar(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // ---------- Alunos ----------
    @GetMapping("/alunos")
    public List<AlunoResponse> listarAlunos() {
        return alunoService.listarTodos();
    }

    @GetMapping("/alunos/{cpf}")
    public ResponseEntity<AlunoResponse> buscarAluno(@PathVariable String cpf) {
        return alunoService.buscarPorCpf(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/alunos")
    public ResponseEntity<AlunoResponse> criarAluno(@RequestBody AlunoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(alunoService.criar(request));
    }

    @DeleteMapping("/alunos/{cpf}")
    public ResponseEntity<Void> deletarAluno(@PathVariable String cpf) {
        alunoService.deletar(cpf);
        return ResponseEntity.noContent().build();
    }

    // ---------- Motoristas ----------
    @GetMapping("/motoristas")
    public List<MotoristaResponse> listarMotoristas() {
        return motoristaService.listarTodos();
    }

    @GetMapping("/motoristas/{cpf}")
    public ResponseEntity<MotoristaResponse> buscarMotorista(@PathVariable String cpf) {
        return motoristaService.buscarPorCpf(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/motoristas")
    public ResponseEntity<MotoristaResponse> criarMotorista(@RequestBody MotoristaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(motoristaService.criar(request));
    }

    @DeleteMapping("/motoristas/{cpf}")
    public ResponseEntity<Void> deletarMotorista(@PathVariable String cpf) {
        motoristaService.deletar(cpf);
        return ResponseEntity.noContent().build();
    }
}