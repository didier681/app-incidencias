package com.didier.incidencias_backend.domain.port.in;

import com.didier.incidencias_backend.domain.model.Usuario;
import java.util.List;

public interface UsuarioUseCase {

    List<Usuario> listarTodos();

    Usuario registrar(Usuario usuario);

    void eliminar(Long id);
}
