package com.example.edutransporte.repository;

import com.example.edutransporte.model.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Integer> {

    // Chat da viagem (ignora mensagens apagadas)
    List<Mensagem> findByViagemIdAndDeletadoFalseOrderByCreatedAtAsc(Integer viagemId);

    // Para polling: só o que chegou depois da última mensagem que o cliente já tem
    List<Mensagem> findByViagemIdAndDeletadoFalseAndCreatedAtAfterOrderByCreatedAtAsc(
            Integer viagemId, LocalDateTime depoisDe);
}
