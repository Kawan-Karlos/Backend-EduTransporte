package com.example.edutransporte.service;

import com.example.edutransporte.model.Veiculo;
import com.example.edutransporte.repository.VeiculoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;

    public VeiculoService(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    public List<Veiculo> listarTodos() {
        return veiculoRepository.findAll();
    }

    public Optional<Veiculo> buscarPorPlaca(String placa) {
        return veiculoRepository.findById(placa);
    }

    public Veiculo criar(Veiculo veiculo) {
        if (veiculoRepository.existsById(veiculo.getPlaca())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Placa já cadastrada");
        }
        return veiculoRepository.save(veiculo);
    }

    public void deletar(String placa) {
        veiculoRepository.deleteById(placa);
    }
}