package com.didier.incidencias_backend.application.service;

import com.didier.incidencias_backend.domain.model.Categoria;
import com.didier.incidencias_backend.domain.port.in.CategoriaUseCase;
import com.didier.incidencias_backend.domain.port.out.CategoriaRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaUseCase {

    private final CategoriaRepositoryPort categoriaRepositoryPort;

    public CategoriaServiceImpl(CategoriaRepositoryPort categoriaRepositoryPort) {
        this.categoriaRepositoryPort = categoriaRepositoryPort;
    }

    @Override
    public List<Categoria> listarTodas() {
        return categoriaRepositoryPort.listarTodas();
    }

    @Override
    public Categoria crear(Categoria categoria) {
        return categoriaRepositoryPort.guardar(categoria);
    }

    @Override
    public void eliminar(Long id) {
        categoriaRepositoryPort.eliminar(id);
    }
}
