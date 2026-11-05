package com.didier.incidencias_backend.infrastructure.adapter.out.persistence;

import com.didier.incidencias_backend.domain.model.EstadoIncidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IncidenciaJpaRepository extends JpaRepository<IncidenciaJpaEntity, Long> {

    List<IncidenciaJpaEntity> findAllByOrderByFechaCreacionDesc();

    List<IncidenciaJpaEntity> findByEstadoOrderByFechaCreacionDesc(EstadoIncidencia estado);

    List<IncidenciaJpaEntity> findByCategoriaIdOrderByFechaCreacionDesc(Long categoriaId);

    List<IncidenciaJpaEntity> findByEstadoAndCategoriaIdOrderByFechaCreacionDesc(EstadoIncidencia estado, Long categoriaId);

    List<IncidenciaJpaEntity> findByUsuarioReportaIdOrderByFechaCreacionDesc(Long usuarioId);
}
