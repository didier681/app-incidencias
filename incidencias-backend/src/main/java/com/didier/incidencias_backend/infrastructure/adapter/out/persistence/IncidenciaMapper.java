package com.didier.incidencias_backend.infrastructure.adapter.out.persistence;

import com.didier.incidencias_backend.domain.model.Incidencia;
import org.springframework.stereotype.Component;

@Component
public class IncidenciaMapper {

    private final UsuarioMapper usuarioMapper;
    private final CategoriaMapper categoriaMapper;

    public IncidenciaMapper(UsuarioMapper usuarioMapper, CategoriaMapper categoriaMapper) {
        this.usuarioMapper = usuarioMapper;
        this.categoriaMapper = categoriaMapper;
    }

    public Incidencia toDomain(IncidenciaJpaEntity entity) {
        return new Incidencia(
                entity.getId(),
                entity.getTitulo(),
                entity.getDescripcion(),
                entity.getUbicacion(),
                entity.getEstado(),
                entity.getPrioridad(),
                entity.getFechaCreacion(),
                usuarioMapper.toDomain(entity.getUsuarioReporta()),
                categoriaMapper.toDomain(entity.getCategoria())
        );
    }

    public IncidenciaJpaEntity toEntity(Incidencia incidencia) {
        IncidenciaJpaEntity entity = new IncidenciaJpaEntity();
        entity.setId(incidencia.getId());
        entity.setTitulo(incidencia.getTitulo());
        entity.setDescripcion(incidencia.getDescripcion());
        entity.setUbicacion(incidencia.getUbicacion());
        entity.setEstado(incidencia.getEstado());
        entity.setPrioridad(incidencia.getPrioridad());
        entity.setFechaCreacion(incidencia.getFechaCreacion());
        entity.setUsuarioReporta(usuarioMapper.toEntity(incidencia.getUsuarioReporta()));
        entity.setCategoria(categoriaMapper.toEntity(incidencia.getCategoria()));
        return entity;
    }
}
