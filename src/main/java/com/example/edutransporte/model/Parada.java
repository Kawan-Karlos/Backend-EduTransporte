package com.example.edutransporte.model;

import com.example.edutransporte.model.id.ParadaId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Entity
@Table(name = "parada")
@Getter
@Setter
@NoArgsConstructor
public class Parada {

    @EmbeddedId
    private ParadaId id = new ParadaId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("viagemId")
    @JoinColumn(name = "viagem_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Viagem viagem;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(nullable = false, precision = 11, scale = 8)
    private BigDecimal longitude;
}
