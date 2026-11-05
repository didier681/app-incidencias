package com.didier.incidencias_backend.domain.port.in;

import com.didier.incidencias_backend.domain.model.Categoria;
import java.util.List;

public interface CategoriaUseCase {

    List<Categoria> listarTodas();

    Categoria crear(Categoria categoria);

    void eliminar(Long id);
}
