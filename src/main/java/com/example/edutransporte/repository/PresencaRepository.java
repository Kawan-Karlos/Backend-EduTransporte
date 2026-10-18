package com.example.edutransporte.repository;

import com.example.edutransporte.model.Presenca;
import com.example.edutransporte.model.id.PresencaId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PresencaRepository extends JpaRepository<Presenca, PresencaId> {

    List<Presenca> findByIdViagemId(Integer viagemId);

    List<Presenca> findByIdAlunoCpf(String alunoCpf);

    Optional<Presenca> findByIdAlunoCpfAndIdViagemId(String alunoCpf, Integer viagemId);
}