package com.didier.incidencias_backend.application.service;

import com.didier.incidencias_backend.domain.model.EstadoServicio;
import com.didier.incidencias_backend.domain.model.RecursoNoEncontradoException;
import com.didier.incidencias_backend.domain.model.Rol;
import com.didier.incidencias_backend.domain.model.ServicioComunitario;
import com.didier.incidencias_backend.domain.model.Usuario;
import com.didier.incidencias_backend.domain.port.out.ServicioComunitarioRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ServicioComunitarioServiceImplTest {

    private static final String CORREO = "ana@correo.com";
    private static final String OTRO_CORREO = "luis@correo.com";

    private FakeServicioRepository servicios;
    private ServicioComunitarioServiceImpl servicio;

    @BeforeEach
    void setUp() {
        servicios = new FakeServicioRepository();
        FakeUsuarioRepository usuarios = new FakeUsuarioRepository();
        usuarios.guardar(new Usuario(1L, "Ana", CORREO, "hash", Rol.USUARIO_GENERAL));
        usuarios.guardar(new Usuario(2L, "Luis", OTRO_CORREO, "hash", Rol.USUARIO_GENERAL));
        servicio = new ServicioComunitarioServiceImpl(servicios, usuarios);
    }

    @Test
    void crearDejaEstadoSolicitadoYAsignaUsuarioYFecha() {
        ServicioComunitario creado = servicio.crear(nuevoServicio(), CORREO);

        assertNotNull(creado.getId());
        assertEquals(EstadoServicio.SOLICITADO, creado.getEstado());
        assertNotNull(creado.getFechaCreacion());
        assertEquals(CORREO, creado.getUsuario().getCorreo());
    }

    @Test
    void crearConUsuarioInexistenteLanzaExcepcion() {
        ServicioComunitario nuevo = nuevoServicio();

        assertThrows(RecursoNoEncontradoException.class, () -> servicio.crear(nuevo, "nadie@correo.com"));
    }

    @Test
    void listarTodosFiltraPorEstadoSoloSiSeIndica() {
        Long id = servicio.crear(nuevoServicio(), CORREO).getId();
        servicio.crear(nuevoServicio(), CORREO);
        servicio.cambiarEstado(id, EstadoServicio.APROBADO);

        assertEquals(2, servicio.listarTodos(null).size());
        assertEquals(1, servicio.listarTodos(EstadoServicio.APROBADO).size());
    }

    @Test
    void listarPorUsuarioDevuelveSoloSusSolicitudes() {
        servicio.crear(nuevoServicio(), CORREO);
        servicio.crear(nuevoServicio(), OTRO_CORREO);

        List<ServicioComunitario> resultado = servicio.listarPorUsuario(CORREO);

        assertEquals(1, resultado.size());
        assertEquals(CORREO, resultado.get(0).getUsuario().getCorreo());
    }

    @Test
    void cambiarEstadoActualizaElServicio() {
        Long id = servicio.crear(nuevoServicio(), CORREO).getId();

        servicio.cambiarEstado(id, EstadoServicio.APROBADO);

        assertEquals(EstadoServicio.APROBADO, servicios.buscarPorId(id).orElseThrow().getEstado());
    }

    @Test
    void cambiarEstadoDeServicioInexistenteLanzaExcepcion() {
        assertThrows(RecursoNoEncontradoException.class,
                () -> servicio.cambiarEstado(99L, EstadoServicio.APROBADO));
    }

    private ServicioComunitario nuevoServicio() {
        return new ServicioComunitario(null, "Limpieza", "Jornada de limpieza del parque",
                LocalDate.of(2026, 12, 1), null, null, null);
    }

    private static class FakeServicioRepository implements ServicioComunitarioRepositoryPort {

        private final Map<Long, ServicioComunitario> datos = new HashMap<>();
        private long siguienteId = 1;

        @Override
        public ServicioComunitario guardar(ServicioComunitario servicio) {
            if (servicio.getId() == null) {
                servicio.setId(siguienteId++);
            }
            datos.put(servicio.getId(), servicio);
            return servicio;
        }

        @Override
        public Optional<ServicioComunitario> buscarPorId(Long id) {
            return Optional.ofNullable(datos.get(id));
        }

        @Override
        public List<ServicioComunitario> listar(EstadoServicio estado) {
            return datos.values().stream()
                    .filter(s -> estado == null || s.getEstado() == estado)
                    .toList();
        }

        @Override
        public List<ServicioComunitario> listarPorUsuario(Long usuarioId) {
            return datos.values().stream()
                    .filter(s -> s.getUsuario().getId().equals(usuarioId))
                    .toList();
        }
    }
}
