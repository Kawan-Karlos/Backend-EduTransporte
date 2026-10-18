package com.example.edutransporte.repository;

import com.example.edutransporte.model.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensagemRepository extends JpaRepository<Mensagem, Integer> {

    List<Mensagem> findByViagem_IdAndDeletadoFalseOrderByCreatedAtAsc(Integer viagemId);
}