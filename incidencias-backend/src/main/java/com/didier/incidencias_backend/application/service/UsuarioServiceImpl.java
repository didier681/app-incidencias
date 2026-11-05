package com.didier.incidencias_backend.application.service;

import com.didier.incidencias_backend.domain.model.Usuario;
import com.didier.incidencias_backend.domain.port.in.UsuarioUseCase;
import com.didier.incidencias_backend.domain.port.out.PasswordEncoderPort;
import com.didier.incidencias_backend.domain.port.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;

    public UsuarioServiceImpl(UsuarioRepositoryPort usuarioRepositoryPort,
                              PasswordEncoderPort passwordEncoderPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepositoryPort.listarTodos();
    }

    @Override
    public Usuario registrar(Usuario usuario) {
        usuario.setContrasena(passwordEncoderPort.encriptar(usuario.getContrasena()));
        return usuarioRepositoryPort.guardar(usuario);
    }

    @Override
    public void eliminar(Long id) {
        usuarioRepositoryPort.eliminar(id);
    }
}