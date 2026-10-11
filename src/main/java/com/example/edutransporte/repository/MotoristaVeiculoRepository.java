package com.example.edutransporte.repository;

import com.example.edutransporte.model.MotoristaVeiculo;
import com.example.edutransporte.model.id.MotoristaVeiculoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MotoristaVeiculoRepository extends JpaRepository<MotoristaVeiculo, MotoristaVeiculoId> {

    // Atribuição vigente (sem data de desatribuição) de um motorista
    @Query("""
           select mv from MotoristaVeiculo mv
           join fetch mv.veiculo
           where mv.motorista.cpf = :cpf and mv.dataDesatribuicao is null
           """)
    Optional<MotoristaVeiculo> findAtribuicaoAtualDoMotorista(@Param("cpf") String cpf);

    // Atribuição vigente de um veículo (para impedir dois motoristas no mesmo carro)
    @Query("""
           select mv from MotoristaVeiculo mv
           where mv.veiculo.placa = :placa and mv.dataDesatribuicao is null
           """)
    Optional<MotoristaVeiculo> findAtribuicaoAtualDoVeiculo(@Param("placa") String placa);

    // Histórico completo do motorista
    @Query("""
           select mv from MotoristaVeiculo mv
           join fetch mv.veiculo
           where mv.motorista.cpf = :cpf
           order by mv.id.dataAtribuicao desc
           """)
    List<MotoristaVeiculo> findHistoricoDoMotorista(@Param("cpf") String cpf);
}
