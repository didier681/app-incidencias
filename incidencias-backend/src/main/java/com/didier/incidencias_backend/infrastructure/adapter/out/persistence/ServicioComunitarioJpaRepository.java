package com.didier.incidencias_backend.infrastructure.adapter.out.persistence;

import com.didier.incidencias_backend.domain.model.EstadoServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ServicioComunitarioJpaRepository extends JpaRepository<ServicioComunitarioJpaEntity, Long> {

    List<ServicioComunitarioJpaEntity> findAllByOrderByFechaCreacionDesc();

    List<ServicioComunitarioJpaEntity> findByEstadoOrderByFechaCreacionDesc(EstadoServicio estado);

    List<ServicioComunitarioJpaEntity> findByUsuarioIdOrderByFechaCreacionDesc(Long usuarioId);
}
