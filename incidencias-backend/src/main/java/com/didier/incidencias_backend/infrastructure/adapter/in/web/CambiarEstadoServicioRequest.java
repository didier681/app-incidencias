package com.didier.incidencias_backend.infrastructure.adapter.in.web;

import com.didier.incidencias_backend.domain.model.EstadoServicio;
import jakarta.validation.constraints.NotNull;

public record CambiarEstadoServicioRequest(
        @NotNull EstadoServicio estado
) {
}
