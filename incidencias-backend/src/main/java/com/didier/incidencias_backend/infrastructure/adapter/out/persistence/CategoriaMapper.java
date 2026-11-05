package com.didier.incidencias_backend.infrastructure.adapter.out.persistence;

import com.didier.incidencias_backend.domain.model.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public Categoria toDomain(CategoriaJpaEntity entity) {
        return new Categoria(
                entity.getId(),
                entity.getNombre()
        );
    }

    public CategoriaJpaEntity toEntity(Categoria categoria) {
        CategoriaJpaEntity entity = new CategoriaJpaEntity();
        entity.setId(categoria.getId());
        entity.setNombre(categoria.getNombre());
        return entity;
    }
}
