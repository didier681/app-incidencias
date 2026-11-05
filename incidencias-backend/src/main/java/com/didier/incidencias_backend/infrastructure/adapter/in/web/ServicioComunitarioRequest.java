package com.didier.incidencias_backend.infrastructure.adapter.in.web;

import com.didier.incidencias_backend.domain.model.ServicioComunitario;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record ServicioComunitarioRequest(
        @NotBlank String tipo,
        @NotBlank String descripcion,
        LocalDate fechaSolicitada
) {
    public ServicioComunitario toDomain() {
        // Estado, fecha de creación y usuario los asigna el caso de uso
        return new ServicioComunitario(null, tipo, descripcion, fechaSolicitada, null, null, null);
    }
}
