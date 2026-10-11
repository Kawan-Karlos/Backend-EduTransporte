package com.example.edutransporte.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "veiculo")
@Getter
@Setter
@NoArgsConstructor
public class Veiculo {

    @Id
    @Column(length = 10)
    private String placa;

    @Column(length = 50)
    private String modelo;
}
