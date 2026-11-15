package com.example.edutransporte.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_preferencia_notificacao")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreferenciaNotificacao {

    // CPF do usuário logado (subject do JWT) — vale para aluno e motorista
    @Id
    @Column(length = 14)
    private String cpf;

    @Column(nullable = false)
    private boolean avisosImportantes = true;

    @Column(nullable = false)
    private boolean presenca = true;

    @Column(nullable = false)
    private boolean mensagens = true;

    @Column(nullable = false)
    private boolean rotas = true;
}
