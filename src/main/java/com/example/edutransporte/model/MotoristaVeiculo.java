package com.example.edutransporte.model;

import com.example.edutransporte.model.id.MotoristaVeiculoId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "motorista_veiculo")
@Getter
@Setter
@NoArgsConstructor
public class MotoristaVeiculo {

    @EmbeddedId
    private MotoristaVeiculoId id = new MotoristaVeiculoId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("motoristaCpf")
    @JoinColumn(name = "motorista_cpf", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Motorista motorista;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("veiculoPlaca")
    @JoinColumn(name = "veiculo_placa", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Veiculo veiculo;

    @Column(name = "data_desatribuicao")
    private LocalDate dataDesatribuicao;
}
