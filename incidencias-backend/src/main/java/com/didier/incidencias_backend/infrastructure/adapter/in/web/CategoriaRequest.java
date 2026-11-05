package com.didier.incidencias_backend.infrastructure.adapter.in.web;

import com.didier.incidencias_backend.domain.model.Categoria;
import jakarta.validation.constraints.NotBlank;

public record CategoriaRequest(
        @NotBlank String nombre
) {
    public Categoria toDomain() {
        return new Categoria(null, nombre);
    }
}
