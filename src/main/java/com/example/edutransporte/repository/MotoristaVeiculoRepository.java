package com.example.edutransporte.repository;

import com.example.edutransporte.model.MotoristaVeiculo;
import com.example.edutransporte.model.id.MotoristaVeiculoId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MotoristaVeiculoRepository extends JpaRepository<MotoristaVeiculo, MotoristaVeiculoId> {

    List<MotoristaVeiculo> findByIdMotoristaCpf(String motoristaCpf);

    Optional<MotoristaVeiculo> findByIdMotoristaCpfAndIdVeiculoPlaca(String motoristaCpf, String veiculoPlaca);
}