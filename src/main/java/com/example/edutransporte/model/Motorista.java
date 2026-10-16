package com.example.edutransporte.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "motorista")
@Getter
@Setter
@NoArgsConstructor
public class Motorista {

    @Id
    @Column(columnDefinition = "CHAR(11)", length = 11)
    private String cpf;

    @Column(name = "nome_completo", nullable = false, length = 80)
    private String nomeCompleto;

    @Column(nullable = false, unique = true, length = 60)
    private String email;

    @Column(name = "numero_celular", nullable = false, length = 15)
    private String numeroCelular;

    @Column(nullable = false, length = 100)
    private String senha;

    @Column(name = "carteira_conducao", nullable = false, length = 20)
    private String carteiraConducao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cidade_id", nullable = false)
    private Cidade cidade;
}
