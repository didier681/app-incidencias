package com.didier.incidencias_backend.infrastructure.adapter.out.persistence;

import com.didier.incidencias_backend.domain.model.ServicioComunitario;
import org.springframework.stereotype.Component;

@Component
public class ServicioComunitarioMapper {

    private final UsuarioMapper usuarioMapper;

    public ServicioComunitarioMapper(UsuarioMapper usuarioMapper) {
        this.usuarioMapper = usuarioMapper;
    }

    public ServicioComunitario toDomain(ServicioComunitarioJpaEntity entity) {
        return new ServicioComunitario(
                entity.getId(),
                entity.getTipo(),
                entity.getDescripcion(),
                entity.getFechaSolicitada(),
                entity.getEstado(),
                entity.getFechaCreacion(),
                usuarioMapper.toDomain(entity.getUsuario())
        );
    }

    public ServicioComunitarioJpaEntity toEntity(ServicioComunitario servicio) {
        ServicioComunitarioJpaEntity entity = new ServicioComunitarioJpaEntity();
        entity.setId(servicio.getId());
        entity.setTipo(servicio.getTipo());
        entity.setDescripcion(servicio.getDescripcion());
        entity.setFechaSolicitada(servicio.getFechaSolicitada());
        entity.setEstado(servicio.getEstado());
        entity.setFechaCreacion(servicio.getFechaCreacion());
        entity.setUsuario(usuarioMapper.toEntity(servicio.getUsuario()));
        return entity;
    }
}
