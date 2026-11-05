package com.didier.incidencias_backend.application.service;

import com.didier.incidencias_backend.domain.model.Categoria;
import com.didier.incidencias_backend.domain.model.EstadoIncidencia;
import com.didier.incidencias_backend.domain.model.Incidencia;
import com.didier.incidencias_backend.domain.model.RecursoNoEncontradoException;
import com.didier.incidencias_backend.domain.model.Usuario;
import com.didier.incidencias_backend.domain.port.in.IncidenciaUseCase;
import com.didier.incidencias_backend.domain.port.out.CategoriaRepositoryPort;
import com.didier.incidencias_backend.domain.port.out.IncidenciaRepositoryPort;
import com.didier.incidencias_backend.domain.port.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IncidenciaServiceImpl implements IncidenciaUseCase {

    private final IncidenciaRepositoryPort incidenciaRepositoryPort;
    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final CategoriaRepositoryPort categoriaRepositoryPort;

    public IncidenciaServiceImpl(IncidenciaRepositoryPort incidenciaRepositoryPort,
                                 UsuarioRepositoryPort usuarioRepositoryPort,
                                 CategoriaRepositoryPort categoriaRepositoryPort) {
        this.incidenciaRepositoryPort = incidenciaRepositoryPort;
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.categoriaRepositoryPort = categoriaRepositoryPort;
    }

    @Override
    public Incidencia crear(Incidencia incidencia, String correoUsuario) {
        Usuario usuario = buscarUsuario(correoUsuario);
        Long categoriaId = incidencia.getCategoria().getId();
        Categoria categoria = categoriaRepositoryPort.buscarPorId(categoriaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada: " + categoriaId));

        incidencia.setUsuarioReporta(usuario);
        incidencia.setCategoria(categoria);
        incidencia.setEstado(EstadoIncidencia.PENDIENTE);
        incidencia.setFechaCreacion(LocalDateTime.now());
        return incidenciaRepositoryPort.guardar(incidencia);
    }

    @Override
    public List<Incidencia> listarTodas(EstadoIncidencia estado, Long categoriaId) {
        return incidenciaRepositoryPort.listar(estado, categoriaId);
    }

    @Override
    public List<Incidencia> listarPorUsuario(String correoUsuario) {
        return incidenciaRepositoryPort.listarPorUsuario(buscarUsuario(correoUsuario).getId());
    }

    @Override
    public Incidencia cambiarEstado(Long id, EstadoIncidencia estado) {
        Incidencia incidencia = incidenciaRepositoryPort.buscarPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Incidencia no encontrada: " + id));
        incidencia.setEstado(estado);
        return incidenciaRepositoryPort.guardar(incidencia);
    }

    private Usuario buscarUsuario(String correo) {
        return usuarioRepositoryPort.buscarPorCorreo(correo)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado: " + correo));
    }
}
