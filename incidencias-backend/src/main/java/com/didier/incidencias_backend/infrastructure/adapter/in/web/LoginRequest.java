package com.didier.incidencias_backend.infrastructure.adapter.in.web;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(@NotBlank String correo, @NotBlank String contrasena) {
}