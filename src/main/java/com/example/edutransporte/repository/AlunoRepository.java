package com.example.edutransporte.repository;

import com.example.edutransporte.model.Aluno;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, String> {

    // Carrega cidade e faculdade na mesma consulta (evita N+1 e LazyInitializationException)
    @EntityGraph(attributePaths = {"cidade", "faculdade"})
    Optional<Aluno> findByCpf(String cpf);

    @EntityGraph(attributePaths = {"cidade", "faculdade"})
    List<Aluno> findByCidadeId(Integer cidadeId);

    @EntityGraph(attributePaths = {"cidade", "faculdade"})
    List<Aluno> findByFaculdadeId(Integer faculdadeId);

    @EntityGraph(attributePaths = {"cidade", "faculdade"})
    List<Aluno> findByCidadeIdAndFaculdadeId(Integer cidadeId, Integer faculdadeId);

    List<Aluno> findByNomeCompletoContainingIgnoreCase(String nome);
}
