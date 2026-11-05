package com.didier.incidencias_backend.infrastructure.adapter.out.persistence;

import com.didier.incidencias_backend.domain.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toDomain(UsuarioJpaEntity entity) {
        return new Usuario(
                entity.getId(),
                entity.getNombre(),
                entity.getCorreo(),
                entity.getContrasena(),
                entity.getRol()
        );
    }

    public UsuarioJpaEntity toEntity(Usuario usuario) {
        UsuarioJpaEntity entity = new UsuarioJpaEntity();
        entity.setId(usuario.getId());
        entity.setNombre(usuario.getNombre());
        entity.setCorreo(usuario.getCorreo());
        entity.setContrasena(usuario.getContrasena());
        entity.setRol(usuario.getRol());
        return entity;
    }
}