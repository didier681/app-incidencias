package com.didier.incidencias_backend.infrastructure.adapter.out.persistence;

import com.didier.incidencias_backend.domain.model.EstadoServicio;
import com.didier.incidencias_backend.domain.model.ServicioComunitario;
import com.didier.incidencias_backend.domain.port.out.ServicioComunitarioRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ServicioComunitarioRepositoryAdapter implements ServicioComunitarioRepositoryPort {

    private final ServicioComunitarioJpaRepository jpaRepository;
    private final ServicioComunitarioMapper mapper;

    public ServicioComunitarioRepositoryAdapter(ServicioComunitarioJpaRepository jpaRepository,
                                                ServicioComunitarioMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public ServicioComunitario guardar(ServicioComunitario servicio) {
        ServicioComunitarioJpaEntity guardado = jpaRepository.save(mapper.toEntity(servicio));
        return mapper.toDomain(guardado);
    }

    @Override
    public Optional<ServicioComunitario> buscarPorId(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<ServicioComunitario> listar(EstadoServicio estado) {
        List<ServicioComunitarioJpaEntity> resultado = estado != null
                ? jpaRepository.findByEstadoOrderByFechaCreacionDesc(estado)
                : jpaRepository.findAllByOrderByFechaCreacionDesc();
        return resultado.stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<ServicioComunitario> listarPorUsuario(Long usuarioId) {
        return jpaRepository.findByUsuarioIdOrderByFechaCreacionDesc(usuarioId).stream()
                .map(mapper::toDomain)
                .toList();
    }
}
