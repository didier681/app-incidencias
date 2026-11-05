package com.didier.incidencias_backend.domain.port.out;

import com.didier.incidencias_backend.domain.model.EstadoIncidencia;
import com.didier.incidencias_backend.domain.model.Incidencia;
import java.util.List;
import java.util.Optional;

public interface IncidenciaRepositoryPort {

    Incidencia guardar(Incidencia incidencia);

    Optional<Incidencia> buscarPorId(Long id);

    List<Incidencia> listar(EstadoIncidencia estado, Long categoriaId);

    List<Incidencia> listarPorUsuario(Long usuarioId);
}
