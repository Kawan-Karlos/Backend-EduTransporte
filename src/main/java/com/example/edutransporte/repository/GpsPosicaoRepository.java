package com.example.edutransporte.repository;

import com.example.edutransporte.model.GpsPosicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GpsPosicaoRepository extends JpaRepository<GpsPosicao, Integer> {

    // Posição mais recente do ônibus (mapa em tempo real)
    Optional<GpsPosicao> findFirstByViagemIdOrderByTimestampDesc(Integer viagemId);

    // Trajeto percorrido
    List<GpsPosicao> findByViagemIdOrderByTimestampAsc(Integer viagemId);
}
