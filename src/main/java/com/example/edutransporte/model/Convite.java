package com.example.edutransporte.model;

import com.example.edutransporte.model.converter.TipoUsuarioConverter;
import com.example.edutransporte.model.enums.TipoUsuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "convite")
@Getter
@Setter
@NoArgsConstructor
public class Convite {

    @Id
    @Column(length = 50)
    private String codigo;

    @Column
    private Boolean usado = false;

    @CreationTimestamp
    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;

    @Column(name = "expira_em", nullable = false)
    private LocalDateTime expiraEm;

    @Column(name = "usuario_cpf", columnDefinition = "CHAR(11)", length = 11)
    private String usuarioCpf;

    @Convert(converter = TipoUsuarioConverter.class)
    @Column(name = "usuario_tipo")
    private TipoUsuario usuarioTipo;
}
