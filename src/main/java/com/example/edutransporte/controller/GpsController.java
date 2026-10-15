package com.example.edutransporte.controller;

import com.example.edutransporte.DTOs.GpsRequest;
import com.example.edutransporte.DTOs.GpsResponse;
import com.example.edutransporte.service.GpsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/viagens/{viagemId}/gps")
public class GpsController {

    private final GpsService gpsService;

    public GpsController(GpsService gpsService) {
        this.gpsService = gpsService;
    }

    // App do motorista envia a posição
    @PostMapping
    public ResponseEntity<GpsResponse> registrar(@PathVariable Integer viagemId,
                                                 @RequestBody GpsRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gpsService.registrar(viagemId, request));
    }

    // App do aluno vê onde o ônibus está agora
    @GetMapping("/ultima")
    public ResponseEntity<GpsResponse> ultima(@PathVariable Integer viagemId) {
        return gpsService.ultima(viagemId).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Trajeto completo
    @GetMapping
    public List<GpsResponse> historico(@PathVariable Integer viagemId) {
        return gpsService.historico(viagemId);
    }
}