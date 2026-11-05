package com.didier.incidencias_backend.domain.port.out;

public interface PasswordEncoderPort {

    String encriptar(String contrasenaPlana);

    boolean coincide(String contrasenaPlana, String contrasenaEncriptada);
}