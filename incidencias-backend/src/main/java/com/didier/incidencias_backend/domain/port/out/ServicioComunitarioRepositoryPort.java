package com.didier.incidencias_backend.domain.port.out;

import com.didier.incidencias_backend.domain.model.EstadoServicio;
import com.didier.incidencias_backend.domain.model.ServicioComunitario;
import java.util.List;
import java.util.Optional;

public interface ServicioComunitarioRepositoryPort {

    ServicioComunitario guardar(ServicioComunitario servicio);

    Optional<ServicioComunitario> buscarPorId(Long id);

    List<ServicioComunitario> listar(EstadoServicio estado);

    List<ServicioComunitario> listarPorUsuario(Long usuarioId);
}
