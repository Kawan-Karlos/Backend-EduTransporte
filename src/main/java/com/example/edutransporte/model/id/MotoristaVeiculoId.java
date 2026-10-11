package com.example.edutransporte.model.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MotoristaVeiculoId implements Serializable {

    @Column(name = "motorista_cpf", columnDefinition = "CHAR(11)", length = 11)
    private String motoristaCpf;

    @Column(name = "veiculo_placa", length = 10)
    private String veiculoPlaca;

    @Column(name = "data_atribuicao")
    private LocalDate dataAtribuicao;
}
