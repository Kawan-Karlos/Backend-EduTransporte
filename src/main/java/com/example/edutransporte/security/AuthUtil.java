package com.example.edutransporte.security;

import com.example.edutransporte.model.enums.TipoUsuario;
import org.springframework.security.core.Authentication;

public final class AuthUtil {

    private AuthUtil() {}

    public static TipoUsuario tipo(Authentication auth) {
        String role = auth.getAuthorities().iterator().next().getAuthority();
        return TipoUsuario.valueOf(role.replace("ROLE_", ""));
    }
}