package com.didier.incidencias_backend.infrastructure.adapter.in.web;

import com.didier.incidencias_backend.domain.model.Categoria;

public record CategoriaResponse(Long id, String nombre) {

    public static CategoriaResponse desde(Categoria categoria) {
        return new CategoriaResponse(
                categoria.getId(),
                categoria.getNombre()
        );
    }
}
