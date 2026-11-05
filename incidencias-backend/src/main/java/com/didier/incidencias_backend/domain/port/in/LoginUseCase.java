package com.didier.incidencias_backend.domain.port.in;

import com.didier.incidencias_backend.domain.model.ResultadoLogin;

public interface LoginUseCase {

    ResultadoLogin login(String correo, String contrasena);
}
