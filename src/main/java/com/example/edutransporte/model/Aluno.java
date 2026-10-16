package com.example.edutransporte.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "aluno")
@Getter
@Setter
@NoArgsConstructor
public class Aluno {

    @Id
    @Column(columnDefinition = "CHAR(11)", length = 11)
    private String cpf;

    @Column(name = "nome_completo", nullable = false, length = 80)
    private String nomeCompleto;

    @Column(name = "numero_celular", nullable = false, length = 15)
    private String numeroCelular;

    @Column(nullable = false, length = 100)
    private String senha;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cidade_id", nullable = false)
    private Cidade cidade;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "faculdade_id", nullable = false)
    private Faculdade faculdade;
}
