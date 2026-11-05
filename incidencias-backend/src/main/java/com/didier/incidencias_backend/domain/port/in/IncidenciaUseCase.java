package com.didier.incidencias_backend.domain.port.in;

import com.didier.incidencias_backend.domain.model.EstadoIncidencia;
import com.didier.incidencias_backend.domain.model.Incidencia;
import java.util.List;

public interface IncidenciaUseCase {

    Incidencia crear(Incidencia incidencia, String correoUsuario);

    List<Incidencia> listarTodas(EstadoIncidencia estado, Long categoriaId);

    List<Incidencia> listarPorUsuario(String correoUsuario);

    Incidencia cambiarEstado(Long id, EstadoIncidencia estado);
}
