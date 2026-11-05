package com.didier.incidencias_backend.infrastructure.adapter.out.persistence;

import com.didier.incidencias_backend.domain.model.Usuario;
import com.didier.incidencias_backend.domain.port.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {

    private final UsuarioJpaRepository jpaRepository;
    private final UsuarioMapper mapper;

    public UsuarioRepositoryAdapter(UsuarioJpaRepository jpaRepository, UsuarioMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Usuario> listarTodos() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        UsuarioJpaEntity guardado = jpaRepository.save(mapper.toEntity(usuario));
        return mapper.toDomain(guardado);
    }

    @Override
    public Optional<Usuario> buscarPorCorreo(String correo) {
        return jpaRepository.findByCorreo(correo).map(mapper::toDomain);
    }

    @Override
    public void eliminar(Long id) {
        jpaRepository.deleteById(id);
    }
}