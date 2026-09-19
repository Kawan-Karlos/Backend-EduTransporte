package com.example.edutransporte.repository;

import com.example.edutransporte.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Método auxiliar para buscar utilizador pelo email (ex: para login/autenticação)
    Optional<Usuario> findByEmail(String email);
}
