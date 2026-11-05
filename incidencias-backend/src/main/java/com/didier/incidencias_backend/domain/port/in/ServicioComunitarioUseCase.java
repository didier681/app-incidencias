package com.didier.incidencias_backend.domain.port.in;

import com.didier.incidencias_backend.domain.model.EstadoServicio;
import com.didier.incidencias_backend.domain.model.ServicioComunitario;
import java.util.List;

public interface ServicioComunitarioUseCase {

    ServicioComunitario crear(ServicioComunitario servicio, String correoUsuario);

    List<ServicioComunitario> listarTodos(EstadoServicio estado);

    List<ServicioComunitario> listarPorUsuario(String correoUsuario);

    ServicioComunitario cambiarEstado(Long id, EstadoServicio estado);
}
