package com.example.edutransporte.service;

import com.example.edutransporte.DTOs.AuthRequest;
import com.example.edutransporte.DTOs.AuthResponse;
import com.example.edutransporte.model.enums.TipoUsuario;
import com.example.edutransporte.repository.AlunoRepository;
import com.example.edutransporte.repository.MotoristaRepository;
import com.example.edutransporte.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final AlunoRepository alunoRepository;
    private final MotoristaRepository motoristaRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(AlunoRepository alunoRepository,
                       MotoristaRepository motoristaRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.alunoRepository = alunoRepository;
        this.motoristaRepository = motoristaRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional(readOnly = true)
    public AuthResponse login(AuthRequest r) {
        var aluno = alunoRepository.findById(r.cpf());
        if (aluno.isPresent() && passwordEncoder.matches(r.senha(), aluno.get().getSenha())) {
            return new AuthResponse(jwtService.gerar(r.cpf(), TipoUsuario.ALUNO), TipoUsuario.ALUNO);
        }

        var motorista = motoristaRepository.findById(r.cpf());
        if (motorista.isPresent() && passwordEncoder.matches(r.senha(), motorista.get().getSenha())) {
            return new AuthResponse(jwtService.gerar(r.cpf(), TipoUsuario.MOTORISTA), TipoUsuario.MOTORISTA);
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "CPF ou senha inválidos");
    }
}