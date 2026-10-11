package com.example.edutransporte.model;

import com.example.edutransporte.model.converter.TipoAvisoConverter;
import com.example.edutransporte.model.enums.TipoAviso;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "aviso")
@Getter
@Setter
@NoArgsConstructor
public class Aviso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Convert(converter = TipoAvisoConverter.class)
    @Column(nullable = false)
    private TipoAviso tipo;

    @Column(length = 50)
    private String icon;

    @CreationTimestamp
    @Column(name = "data", updatable = false)
    private LocalDateTime data;

    @Column
    private Boolean ativo = true;
}
