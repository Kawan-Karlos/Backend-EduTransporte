package com.example.edutransporte.repository;

import com.example.edutransporte.model.Faculdade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FaculdadeRepository extends JpaRepository<Faculdade, Integer> {

    Optional<Faculdade> findByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCase(String nome);
}
