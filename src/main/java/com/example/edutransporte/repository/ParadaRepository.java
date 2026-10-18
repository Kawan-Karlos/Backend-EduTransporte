package com.example.edutransporte.repository;

import com.example.edutransporte.model.Parada;
import com.example.edutransporte.model.id.ParadaId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParadaRepository extends JpaRepository<Parada, ParadaId> {

    List<Parada> findByIdViagemIdOrderByIdOrdem(Integer viagemId);

    Optional<Parada> findByIdViagemIdAndIdOrdem(Integer viagemId, Integer ordem);

    boolean existsByIdViagemIdAndIdOrdem(Integer viagemId, Integer ordem);
}