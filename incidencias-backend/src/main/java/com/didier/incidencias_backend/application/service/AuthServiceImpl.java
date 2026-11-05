package com.didier.incidencias_backend.application.service;

import com.didier.incidencias_backend.domain.model.CredencialesInvalidasException;
import com.didier.incidencias_backend.domain.model.ResultadoLogin;
import com.didier.incidencias_backend.domain.model.Usuario;
import com.didier.incidencias_backend.domain.port.in.LoginUseCase;
import com.didier.incidencias_backend.domain.port.out.PasswordEncoderPort;
import com.didier.incidencias_backend.domain.port.out.TokenGeneratorPort;
import com.didier.incidencias_backend.domain.port.out.UsuarioRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements LoginUseCase {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final TokenGeneratorPort tokenGeneratorPort;

    public AuthServiceImpl(UsuarioRepositoryPort usuarioRepositoryPort,
                           PasswordEncoderPort passwordEncoderPort,
                           TokenGeneratorPort tokenGeneratorPort) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.tokenGeneratorPort = tokenGeneratorPort;
    }

    @Override
    public ResultadoLogin login(String correo, String contrasena) {
        Usuario usuario = usuarioRepositoryPort.buscarPorCorreo(correo)
                .orElseThrow(CredencialesInvalidasException::new);

        if (!passwordEncoderPort.coincide(contrasena, usuario.getContrasena())) {
            throw new CredencialesInvalidasException();
        }

        String token = tokenGeneratorPort.generarToken(usuario.getCorreo(), usuario.getRol().name());
        return new ResultadoLogin(token, usuario.getRol());
    }
}