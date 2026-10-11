package com.example.edutransporte.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "feriado")
@Getter
@Setter
@NoArgsConstructor
public class Feriado {

    @Id
    @Column(name = "data")
    private LocalDate data;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;
}
