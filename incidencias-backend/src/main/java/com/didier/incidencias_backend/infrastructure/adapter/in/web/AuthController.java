package com.didier.incidencias_backend.infrastructure.adapter.in.web;

import com.didier.incidencias_backend.domain.model.ResultadoLogin;
import com.didier.incidencias_backend.domain.model.Rol;
import com.didier.incidencias_backend.domain.model.Usuario;
import com.didier.incidencias_backend.domain.port.in.LoginUseCase;
import com.didier.incidencias_backend.domain.port.in.UsuarioUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final UsuarioUseCase usuarioUseCase;

    public AuthController(LoginUseCase loginUseCase, UsuarioUseCase usuarioUseCase) {
        this.loginUseCase = loginUseCase;
        this.usuarioUseCase = usuarioUseCase;
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        ResultadoLogin resultado = loginUseCase.login(request.correo(), request.contrasena());
        return new LoginResponse(resultado.token(), resultado.rol().name());
    }

    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponse> registro(@Valid @RequestBody RegistroRequest request) {
        Usuario nuevo = new Usuario(null, request.nombre(), request.correo(),
                request.contrasena(), Rol.USUARIO_GENERAL);
        Usuario creado = usuarioUseCase.registrar(nuevo);
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioResponse.desde(creado));
    }
}