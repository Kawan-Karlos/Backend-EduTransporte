package com.example.edutransporte.service;

import com.example.edutransporte.model.Aviso;
import com.example.edutransporte.repository.AvisoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvisoService {

    private final AvisoRepository avisoRepository;

    // Injeção por Construtor (Boa Prática do Spring)
    public AvisoService(AvisoRepository avisoRepository) {
        this.avisoRepository = avisoRepository;
    }

    public List<Aviso> listarTodos() {
        return avisoRepository.findAll();
    }

    public Optional<Aviso> buscarPorId(Long id) {
        return avisoRepository.findById(id);
    }

    public Aviso salvar(Aviso aviso) {
        return avisoRepository.save(aviso);
    }

    public void deletar(Long id) {
        avisoRepository.deleteById(id);
    }
}