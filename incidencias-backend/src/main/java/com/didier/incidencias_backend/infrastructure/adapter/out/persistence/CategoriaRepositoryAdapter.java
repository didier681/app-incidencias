package com.didier.incidencias_backend.infrastructure.adapter.out.persistence;

import com.didier.incidencias_backend.domain.model.Categoria;
import com.didier.incidencias_backend.domain.port.out.CategoriaRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CategoriaRepositoryAdapter implements CategoriaRepositoryPort {

    private final CategoriaJpaRepository jpaRepository;
    private final CategoriaMapper mapper;

    public CategoriaRepositoryAdapter(CategoriaJpaRepository jpaRepository, CategoriaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Categoria> listarTodas() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Categoria guardar(Categoria categoria) {
        CategoriaJpaEntity guardada = jpaRepository.save(mapper.toEntity(categoria));
        return mapper.toDomain(guardada);
    }

    @Override
    public Optional<Categoria> buscarPorId(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public void eliminar(Long id) {
        jpaRepository.deleteById(id);
    }
}
