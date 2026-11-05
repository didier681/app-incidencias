package com.didier.incidencias_backend.infrastructure.adapter.in.web;

import com.didier.incidencias_backend.domain.model.EstadoServicio;
import com.didier.incidencias_backend.domain.model.ServicioComunitario;
import com.didier.incidencias_backend.domain.port.in.ServicioComunitarioUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios-comunitarios")
public class ServicioComunitarioController {

    private final ServicioComunitarioUseCase servicioUseCase;

    public ServicioComunitarioController(ServicioComunitarioUseCase servicioUseCase) {
        this.servicioUseCase = servicioUseCase;
    }

    @PostMapping
    public ResponseEntity<ServicioComunitarioResponse> crear(@Valid @RequestBody ServicioComunitarioRequest request,
                                                             Authentication authentication) {
        ServicioComunitario creado = servicioUseCase.crear(request.toDomain(), authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(ServicioComunitarioResponse.desde(creado));
    }

    @GetMapping
    public List<ServicioComunitarioResponse> listar(@RequestParam(required = false) EstadoServicio estado) {
        return servicioUseCase.listarTodos(estado).stream()
                .map(ServicioComunitarioResponse::desde)
                .toList();
    }

    @GetMapping("/mis-solicitudes")
    public List<ServicioComunitarioResponse> listarMias(Authentication authentication) {
        return servicioUseCase.listarPorUsuario(authentication.getName()).stream()
                .map(ServicioComunitarioResponse::desde)
                .toList();
    }

    @PatchMapping("/{id}/estado")
    public ServicioComunitarioResponse cambiarEstado(@PathVariable Long id,
                                                     @Valid @RequestBody CambiarEstadoServicioRequest request) {
        return ServicioComunitarioResponse.desde(servicioUseCase.cambiarEstado(id, request.estado()));
    }
}
