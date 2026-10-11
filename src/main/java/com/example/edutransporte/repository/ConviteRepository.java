package com.example.edutransporte.repository;

import com.example.edutransporte.model.Convite;
import com.example.edutransporte.model.enums.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ConviteRepository extends JpaRepository<Convite, String> {

    // Convite que ainda pode ser usado: existe, não foi usado e não expirou
    Optional<Convite> findByCodigoAndUsadoFalseAndExpiraEmAfter(String codigo, LocalDateTime agora);

    Optional<Convite> findByUsuarioCpfAndUsuarioTipo(String usuarioCpf, TipoUsuario usuarioTipo);

    // Limpeza de convites vencidos e não utilizados
    @Modifying
    @Transactional
    @Query("delete from Convite c where c.usado = false and c.expiraEm < :agora")
    int apagarExpirados(@Param("agora") LocalDateTime agora);
}
