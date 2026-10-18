package com.example.edutransporte.service;

import com.example.edutransporte.DTOs.MotoristaVeiculoRequest;
import com.example.edutransporte.DTOs.MotoristaVeiculoResponse;
import com.example.edutransporte.model.Motorista;
import com.example.edutransporte.model.MotoristaVeiculo;
import com.example.edutransporte.model.Veiculo;
import com.example.edutransporte.repository.MotoristaRepository;
import com.example.edutransporte.repository.MotoristaVeiculoRepository;
import com.example.edutransporte.repository.VeiculoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class MotoristaVeiculoService {

    private final MotoristaVeiculoRepository repository;
    private final MotoristaRepository motoristaRepository;
    private final VeiculoRepository veiculoRepository;

    public MotoristaVeiculoService(MotoristaVeiculoRepository repository,
                                   MotoristaRepository motoristaRepository,
                                   VeiculoRepository veiculoRepository) {
        this.repository = repository;
        this.motoristaRepository = motoristaRepository;
        this.veiculoRepository = veiculoRepository;
    }

    @Transactional(readOnly = true)
    public List<MotoristaVeiculoResponse> listarPorMotorista(String motoristaCpf) {
        return repository.findByIdMotoristaCpf(motoristaCpf).stream().map(this::toResponse).toList();
    }

    @Transactional
    public MotoristaVeiculoResponse atribuir(String motoristaCpf, MotoristaVeiculoRequest r) {
        if (repository.findByIdMotoristaCpfAndIdVeiculoPlaca(motoristaCpf, r.veiculoPlaca()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Veículo já atribuído a esse motorista");
        }
        Motorista motorista = motoristaRepository.findById(motoristaCpf)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Motorista não encontrado"));
        Veiculo veiculo = veiculoRepository.findById(r.veiculoPlaca())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Veículo não encontrado"));

        MotoristaVeiculo mv = new MotoristaVeiculo();
        mv.getId().setMotoristaCpf(motoristaCpf);
        mv.getId().setVeiculoPlaca(r.veiculoPlaca());
        mv.setMotorista(motorista);
        mv.setVeiculo(veiculo);
        return toResponse(repository.save(mv));
    }

    /** Encerra a atribuição gravando a data de hoje. */
    @Transactional
    public MotoristaVeiculoResponse desatribuir(String motoristaCpf, String veiculoPlaca) {
        MotoristaVeiculo mv = repository.findByIdMotoristaCpfAndIdVeiculoPlaca(motoristaCpf, veiculoPlaca)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Atribuição não encontrada"));
        mv.setDataDesatribuicao(LocalDate.now());
        return toResponse(repository.save(mv));
    }

    private MotoristaVeiculoResponse toResponse(MotoristaVeiculo mv) {
        return new MotoristaVeiculoResponse(mv.getVeiculo().getPlaca(), mv.getDataDesatribuicao(),
                mv.getDataDesatribuicao() == null);
    }
}