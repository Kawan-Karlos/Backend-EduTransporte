package com.example.edutransporte.repository;

import com.example.edutransporte.model.Motorista;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MotoristaRepository extends JpaRepository<Motorista, String> {

    @EntityGraph(attributePaths = "cidade")
    Optional<Motorista> findByCpf(String cpf);

    @EntityGraph(attributePaths = "cidade")
    Optional<Motorista> findByEmail(String email);

    boolean existsByEmail(String email);

    @EntityGraph(attributePaths = "cidade")
    List<Motorista> findByCidadeId(Integer cidadeId);
}
