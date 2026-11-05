package com.didier.incidencias_backend.infrastructure.adapter.in.web;

import com.didier.incidencias_backend.domain.model.EstadoIncidencia;
import jakarta.validation.constraints.NotNull;

public record CambiarEstadoRequest(
        @NotNull EstadoIncidencia estado
) {
}
