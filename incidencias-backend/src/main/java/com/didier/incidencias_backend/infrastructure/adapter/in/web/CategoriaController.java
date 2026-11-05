package com.didier.incidencias_backend.infrastructure.adapter.in.web;

import com.didier.incidencias_backend.domain.model.Categoria;
import com.didier.incidencias_backend.domain.port.in.CategoriaUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaUseCase categoriaUseCase;

    public CategoriaController(CategoriaUseCase categoriaUseCase) {
        this.categoriaUseCase = categoriaUseCase;
    }

    @GetMapping
    public List<CategoriaResponse> listar() {
        return categoriaUseCase.listarTodas().stream()
                .map(CategoriaResponse::desde)
                .toList();
    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> crear(@Valid @RequestBody CategoriaRequest request) {
        Categoria creada = categoriaUseCase.crear(request.toDomain());
        return ResponseEntity.status(HttpStatus.CREATED).body(CategoriaResponse.desde(creada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        categoriaUseCase.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
