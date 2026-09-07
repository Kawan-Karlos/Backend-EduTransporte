package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/avisos")
public class AvisoController {

    @GetMapping
    public List<String> listarAvisos() {

        return List.of("Aviso 1", "Aviso 2");
    }
}

