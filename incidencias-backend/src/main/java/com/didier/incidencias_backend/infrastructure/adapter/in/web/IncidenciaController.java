package com.didier.incidencias_backend.infrastructure.adapter.in.web;

import com.didier.incidencias_backend.domain.model.EstadoIncidencia;
import com.didier.incidencias_backend.domain.model.Incidencia;
import com.didier.incidencias_backend.domain.port.in.IncidenciaUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidencias")
public class IncidenciaController {

    private final IncidenciaUseCase incidenciaUseCase;

    public IncidenciaController(IncidenciaUseCase incidenciaUseCase) {
        this.incidenciaUseCase = incidenciaUseCase;
    }

    @PostMapping
    public ResponseEntity<IncidenciaResponse> crear(@Valid @RequestBody IncidenciaRequest request,
                                                    Authentication authentication) {
        Incidencia creada = incidenciaUseCase.crear(request.toDomain(), authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(IncidenciaResponse.desde(creada));
    }

    @GetMapping
    public List<IncidenciaResponse> listar(@RequestParam(required = false) EstadoIncidencia estado,
                                           @RequestParam(required = false) Long categoriaId) {
        return incidenciaUseCase.listarTodas(estado, categoriaId).stream()
                .map(IncidenciaResponse::desde)
                .toList();
    }

    @GetMapping("/mis-incidencias")
    public List<IncidenciaResponse> listarMias(Authentication authentication) {
        return incidenciaUseCase.listarPorUsuario(authentication.getName()).stream()
                .map(IncidenciaResponse::desde)
                .toList();
    }

    @PatchMapping("/{id}/estado")
    public IncidenciaResponse cambiarEstado(@PathVariable Long id,
                                            @Valid @RequestBody CambiarEstadoRequest request) {
        return IncidenciaResponse.desde(incidenciaUseCase.cambiarEstado(id, request.estado()));
    }
}
