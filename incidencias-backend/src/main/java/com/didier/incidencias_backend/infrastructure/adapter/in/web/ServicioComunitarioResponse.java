package com.didier.incidencias_backend.infrastructure.adapter.in.web;

import com.didier.incidencias_backend.domain.model.EstadoServicio;
import com.didier.incidencias_backend.domain.model.ServicioComunitario;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ServicioComunitarioResponse(
        Long id,
        String tipo,
        String descripcion,
        LocalDate fechaSolicitada,
        EstadoServicio estado,
        LocalDateTime fechaCreacion,
        UsuarioResponse usuario
) {

    public static ServicioComunitarioResponse desde(ServicioComunitario servicio) {
        return new ServicioComunitarioResponse(
                servicio.getId(),
                servicio.getTipo(),
                servicio.getDescripcion(),
                servicio.getFechaSolicitada(),
                servicio.getEstado(),
                servicio.getFechaCreacion(),
                UsuarioResponse.desde(servicio.getUsuario())
        );
    }
}
