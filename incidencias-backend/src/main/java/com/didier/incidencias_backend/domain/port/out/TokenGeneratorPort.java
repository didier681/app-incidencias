package com.didier.incidencias_backend.domain.port.out;

public interface TokenGeneratorPort {

    String generarToken(String correo, String rol);
}