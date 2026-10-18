package com.example.edutransporte.controller;

import com.example.edutransporte.DTOs.AuthRequest;
import com.example.edutransporte.DTOs.AuthResponse;
import com.example.edutransporte.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest r) {
        return authService.login(r);
    }
}