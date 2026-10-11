package com.example.edutransporte.model;

import com.example.edutransporte.model.id.PresencaId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "presenca")
@Getter
@Setter
@NoArgsConstructor
public class Presenca {

    @EmbeddedId
    private PresencaId id = new PresencaId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("alunoCpf")
    @JoinColumn(name = "aluno_cpf", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Aluno aluno;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("viagemId")
    @JoinColumn(name = "viagem_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Viagem viagem;

    @Column
    private Boolean presente = true;

    @CreationTimestamp
    @Column(name = "confirmado_em", updatable = false)
    private LocalDateTime confirmadoEm;
}
