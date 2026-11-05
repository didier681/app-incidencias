package com.didier.incidencias_backend.application.service;

import com.didier.incidencias_backend.domain.model.EstadoServicio;
import com.didier.incidencias_backend.domain.model.RecursoNoEncontradoException;
import com.didier.incidencias_backend.domain.model.ServicioComunitario;
import com.didier.incidencias_backend.domain.model.Usuario;
import com.didier.incidencias_backend.domain.port.in.ServicioComunitarioUseCase;
import com.didier.incidencias_backend.domain.port.out.ServicioComunitarioRepositoryPort;
import com.didier.incidencias_backend.domain.port.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServicioComunitarioServiceImpl implements ServicioComunitarioUseCase {

    private final ServicioComunitarioRepositoryPort servicioRepositoryPort;
    private final UsuarioRepositoryPort usuarioRepositoryPort;

    public ServicioComunitarioServiceImpl(ServicioComunitarioRepositoryPort servicioRepositoryPort,
                                          UsuarioRepositoryPort usuarioRepositoryPort) {
        this.servicioRepositoryPort = servicioRepositoryPort;
        this.usuarioRepositoryPort = usuarioRepositoryPort;
    }

    @Override
    public ServicioComunitario crear(ServicioComunitario servicio, String correoUsuario) {
        servicio.setUsuario(buscarUsuario(correoUsuario));
        servicio.setEstado(EstadoServicio.SOLICITADO);
        servicio.setFechaCreacion(LocalDateTime.now());
        return servicioRepositoryPort.guardar(servicio);
    }

    @Override
    public List<ServicioComunitario> listarTodos(EstadoServicio estado) {
        return servicioRepositoryPort.listar(estado);
    }

    @Override
    public List<ServicioComunitario> listarPorUsuario(String correoUsuario) {
        return servicioRepositoryPort.listarPorUsuario(buscarUsuario(correoUsuario).getId());
    }

    @Override
    public ServicioComunitario cambiarEstado(Long id, EstadoServicio estado) {
        ServicioComunitario servicio = servicioRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Servicio comunitario no encontrado: " + id));
        servicio.setEstado(estado);
        return servicioRepositoryPort.guardar(servicio);
    }

    private Usuario buscarUsuario(String correo) {
        return usuarioRepositoryPort.buscarPorCorreo(correo)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado: " + correo));
    }
}
