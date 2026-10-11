package com.example.edutransporte.model;

import com.example.edutransporte.model.converter.TipoUsuarioConverter;
import com.example.edutransporte.model.enums.TipoUsuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "mensagem")
@Getter
@Setter
@NoArgsConstructor
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String texto;

    // Sem FK no banco: pode ser CPF de aluno OU de motorista (ver remetenteTipo)
    @Column(name = "remetente_cpf", nullable = false, columnDefinition = "CHAR(11)", length = 11)
    private String remetenteCpf;

    @Convert(converter = TipoUsuarioConverter.class)
    @Column(name = "remetente_tipo", nullable = false)
    private TipoUsuario remetenteTipo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "viagem_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Viagem viagem;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column
    private Boolean deletado = false;
}
