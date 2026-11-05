package com.didier.incidencias_backend.infrastructure.adapter.in.web;

import com.didier.incidencias_backend.domain.model.Rol;
import com.didier.incidencias_backend.domain.model.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRequest(
        @NotBlank String nombre,
        @NotBlank @Email String correo,
        @NotBlank @Size(min = 8) String contrasena,
        @NotNull Rol rol
) {
    public Usuario toDomain() {
        return new Usuario(null, nombre, correo, contrasena, rol);
    }
}