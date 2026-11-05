package com.didier.incidencias_backend.infrastructure.adapter.in.web;

import com.didier.incidencias_backend.domain.model.Categoria;
import com.didier.incidencias_backend.domain.model.Incidencia;
import com.didier.incidencias_backend.domain.model.PrioridadIncidencia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record IncidenciaRequest(
        @NotBlank String titulo,
        @NotBlank String descripcion,
        @NotBlank String ubicacion,
        @NotNull PrioridadIncidencia prioridad,
        @NotNull Long categoriaId
) {
    public Incidencia toDomain() {
        // Estado, fecha y usuario los asigna el caso de uso
        return new Incidencia(null, titulo, descripcion, ubicacion, null, prioridad, null,
                null, new Categoria(categoriaId, null));
    }
}
