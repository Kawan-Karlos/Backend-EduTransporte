package com.example.edutransporte.repository;

import com.example.edutransporte.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

    Optional<Cidade> findByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCase(String nome);
}
