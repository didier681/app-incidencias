package com.didier.incidencias_backend.domain.port.out;

import com.didier.incidencias_backend.domain.model.Categoria;
import java.util.List;
import java.util.Optional;

public interface CategoriaRepositoryPort {

    List<Categoria> listarTodas();

    Categoria guardar(Categoria categoria);

    Optional<Categoria> buscarPorId(Long id);

    void eliminar(Long id);
}
