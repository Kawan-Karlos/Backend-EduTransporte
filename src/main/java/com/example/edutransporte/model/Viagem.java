package com.example.edutransporte.model;

import com.example.edutransporte.model.converter.TipoViagemConverter;
import com.example.edutransporte.model.enums.TipoViagem;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "viagem")
@Getter
@Setter
@NoArgsConstructor
public class Viagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome_rota", nullable = false, length = 100)
    private String nomeRota;

    @Convert(converter = TipoViagemConverter.class)
    @Column(nullable = false)
    private TipoViagem tipo;

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @Column(nullable = false)
    private LocalTime horario;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "motorista_cpf", nullable = false)
    private Motorista motorista;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "veiculo_placa", nullable = false)
    private Veiculo veiculo;
}
