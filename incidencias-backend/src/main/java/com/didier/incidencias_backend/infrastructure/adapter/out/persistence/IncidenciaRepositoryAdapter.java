package com.didier.incidencias_backend.infrastructure.adapter.out.persistence;

import com.didier.incidencias_backend.domain.model.EstadoIncidencia;
import com.didier.incidencias_backend.domain.model.Incidencia;
import com.didier.incidencias_backend.domain.port.out.IncidenciaRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class IncidenciaRepositoryAdapter implements IncidenciaRepositoryPort {

    private final IncidenciaJpaRepository jpaRepository;
    private final IncidenciaMapper mapper;

    public IncidenciaRepositoryAdapter(IncidenciaJpaRepository jpaRepository, IncidenciaMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Incidencia guardar(Incidencia incidencia) {
        IncidenciaJpaEntity guardada = jpaRepository.save(mapper.toEntity(incidencia));
        return mapper.toDomain(guardada);
    }

    @Override
    public Optional<Incidencia> buscarPorId(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Incidencia> listar(EstadoIncidencia estado, Long categoriaId) {
        List<IncidenciaJpaEntity> resultado;
        if (estado != null && categoriaId != null) {
            resultado = jpaRepository.findByEstadoAndCategoriaIdOrderByFechaCreacionDesc(estado, categoriaId);
        } else if (estado != null) {
            resultado = jpaRepository.findByEstadoOrderByFechaCreacionDesc(estado);
        } else if (categoriaId != null) {
            resultado = jpaRepository.findByCategoriaIdOrderByFechaCreacionDesc(categoriaId);
        } else {
            resultado = jpaRepository.findAllByOrderByFechaCreacionDesc();
        }
        return resultado.stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<Incidencia> listarPorUsuario(Long usuarioId) {
        return jpaRepository.findByUsuarioReportaIdOrderByFechaCreacionDesc(usuarioId).stream()
                .map(mapper::toDomain)
                .toList();
    }
}
