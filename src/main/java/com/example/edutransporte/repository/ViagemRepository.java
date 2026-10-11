package com.example.edutransporte.repository;

import com.example.edutransporte.model.Viagem;
import com.example.edutransporte.model.enums.TipoViagem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ViagemRepository extends JpaRepository<Viagem, Integer> {

    @EntityGraph(attributePaths = {"motorista", "veiculo"})
    List<Viagem> findByDataOrderByHorarioAsc(LocalDate data);

    @EntityGraph(attributePaths = {"motorista", "veiculo"})
    List<Viagem> findByDataAndTipoOrderByHorarioAsc(LocalDate data, TipoViagem tipo);

    @EntityGraph(attributePaths = {"motorista", "veiculo"})
    List<Viagem> findByMotoristaCpfAndDataOrderByHorarioAsc(String motoristaCpf, LocalDate data);

    @EntityGraph(attributePaths = {"motorista", "veiculo"})
    List<Viagem> findByMotoristaCpfOrderByDataDescHorarioDesc(String motoristaCpf);

    @EntityGraph(attributePaths = {"motorista", "veiculo"})
    List<Viagem> findByDataBetweenOrderByDataAscHorarioAsc(LocalDate inicio, LocalDate fim);
}
