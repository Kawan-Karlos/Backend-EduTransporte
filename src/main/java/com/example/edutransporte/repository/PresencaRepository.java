package com.example.edutransporte.repository;

import com.example.edutransporte.model.Presenca;
import com.example.edutransporte.model.id.PresencaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PresencaRepository extends JpaRepository<Presenca, PresencaId> {

    // Lista de passageiros de uma viagem (com os dados do aluno já carregados)
    @Query("""
           select p from Presenca p
           join fetch p.aluno
           where p.viagem.id = :viagemId
           order by p.aluno.nomeCompleto
           """)
    List<Presenca> findByViagem(@Param("viagemId") Integer viagemId);

    @Query("""
           select p from Presenca p
           join fetch p.aluno
           where p.viagem.id = :viagemId and p.presente = true
           order by p.aluno.nomeCompleto
           """)
    List<Presenca> findConfirmadosByViagem(@Param("viagemId") Integer viagemId);

    @Query("select count(p) from Presenca p where p.viagem.id = :viagemId and p.presente = true")
    long countConfirmadosByViagem(@Param("viagemId") Integer viagemId);

    // Histórico de presenças de um aluno
    @Query("""
           select p from Presenca p
           join fetch p.viagem v
           where p.aluno.cpf = :cpf
           order by v.data desc, v.horario desc
           """)
    List<Presenca> findHistoricoDoAluno(@Param("cpf") String cpf);
}
