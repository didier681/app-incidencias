package com.didier.incidencias_backend.application.service;

import com.didier.incidencias_backend.domain.model.Usuario;
import com.didier.incidencias_backend.domain.port.out.UsuarioRepositoryPort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Implementación falsa en memoria del puerto de usuarios: no usa Spring ni base de datos. */
class FakeUsuarioRepository implements UsuarioRepositoryPort {

    private final List<Usuario> usuarios = new ArrayList<>();

    @Override
    public List<Usuario> listarTodos() {
        return List.copyOf(usuarios);
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        usuarios.add(usuario);
        return usuario;
    }

    @Override
    public Optional<Usuario> buscarPorCorreo(String correo) {
        return usuarios.stream().filter(u -> u.getCorreo().equals(correo)).findFirst();
    }

    @Override
    public void eliminar(Long id) {
        usuarios.removeIf(u -> u.getId().equals(id));
    }
}
