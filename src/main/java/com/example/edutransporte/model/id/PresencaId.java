package com.example.edutransporte.model.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PresencaId implements Serializable {

    @Column(name = "aluno_cpf", columnDefinition = "CHAR(11)", length = 11)
    private String alunoCpf;

    @Column(name = "viagem_id")
    private Integer viagemId;
}
