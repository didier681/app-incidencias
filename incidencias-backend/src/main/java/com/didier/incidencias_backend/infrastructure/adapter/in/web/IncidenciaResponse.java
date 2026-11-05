package com.didier.incidencias_backend.infrastructure.adapter.in.web;

import com.didier.incidencias_backend.domain.model.EstadoIncidencia;
import com.didier.incidencias_backend.domain.model.Incidencia;
import com.didier.incidencias_backend.domain.model.PrioridadIncidencia;

import java.time.LocalDateTime;

public record IncidenciaResponse(
        Long id,
        String titulo,
        String descripcion,
        String ubicacion,
        EstadoIncidencia estado,
        PrioridadIncidencia prioridad,
        LocalDateTime fechaCreacion,
        UsuarioResponse usuarioReporta,
        CategoriaResponse categoria
) {

    public static IncidenciaResponse desde(Incidencia incidencia) {
        return new IncidenciaResponse(
                incidencia.getId(),
                incidencia.getTitulo(),
                incidencia.getDescripcion(),
                incidencia.getUbicacion(),
                incidencia.getEstado(),
                incidencia.getPrioridad(),
                incidencia.getFechaCreacion(),
                UsuarioResponse.desde(incidencia.getUsuarioReporta()),
                CategoriaResponse.desde(incidencia.getCategoria())
        );
    }
}
