package com.didier.incidencias_backend.domain.port.out;

import com.didier.incidencias_backend.domain.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepositoryPort {

    List<Usuario> listarTodos();

    Usuario guardar(Usuario usuario);

    Optional<Usuario> buscarPorCorreo(String correo);

    void eliminar(Long id);
}