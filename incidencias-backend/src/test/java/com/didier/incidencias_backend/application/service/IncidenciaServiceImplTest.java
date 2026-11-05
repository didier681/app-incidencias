package com.didier.incidencias_backend.application.service;

import com.didier.incidencias_backend.domain.model.Categoria;
import com.didier.incidencias_backend.domain.model.EstadoIncidencia;
import com.didier.incidencias_backend.domain.model.Incidencia;
import com.didier.incidencias_backend.domain.model.PrioridadIncidencia;
import com.didier.incidencias_backend.domain.model.RecursoNoEncontradoException;
import com.didier.incidencias_backend.domain.model.Rol;
import com.didier.incidencias_backend.domain.model.Usuario;
import com.didier.incidencias_backend.domain.port.out.CategoriaRepositoryPort;
import com.didier.incidencias_backend.domain.port.out.IncidenciaRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class IncidenciaServiceImplTest {

    private static final String CORREO = "ana@correo.com";
    private static final Long CATEGORIA_ID = 1L;

    private FakeIncidenciaRepository incidencias;
    private IncidenciaServiceImpl servicio;

    @BeforeEach
    void setUp() {
        incidencias = new FakeIncidenciaRepository();
        FakeUsuarioRepository usuarios = new FakeUsuarioRepository();
        usuarios.guardar(new Usuario(1L, "Ana", CORREO, "hash", Rol.USUARIO_GENERAL));
        FakeCategoriaRepository categorias = new FakeCategoriaRepository();
        categorias.guardar(new Categoria(CATEGORIA_ID, "Alumbrado"));
        servicio = new IncidenciaServiceImpl(incidencias, usuarios, categorias);
    }

    @Test
    void crearDejaEstadoPendienteYAsignaUsuarioCategoriaYFecha() {
        Incidencia creada = servicio.crear(nuevaIncidencia(CATEGORIA_ID), CORREO);

        assertNotNull(creada.getId());
        assertEquals(EstadoIncidencia.PENDIENTE, creada.getEstado());
        assertNotNull(creada.getFechaCreacion());
        assertEquals(CORREO, creada.getUsuarioReporta().getCorreo());
        assertEquals("Alumbrado", creada.getCategoria().getNombre());
    }

    @Test
    void crearConCategoriaInexistenteLanzaExcepcion() {
        Incidencia incidencia = nuevaIncidencia(99L);

        assertThrows(RecursoNoEncontradoException.class, () -> servicio.crear(incidencia, CORREO));
        assertTrue(incidencias.listar(null, null).isEmpty());
    }

    @Test
    void cambiarEstadoActualizaLaIncidencia() {
        Long id = servicio.crear(nuevaIncidencia(CATEGORIA_ID), CORREO).getId();

        servicio.cambiarEstado(id, EstadoIncidencia.RESUELTA);

        assertEquals(EstadoIncidencia.RESUELTA, incidencias.buscarPorId(id).orElseThrow().getEstado());
    }

    @Test
    void cambiarEstadoDeIncidenciaInexistenteLanzaExcepcion() {
        assertThrows(RecursoNoEncontradoException.class,
                () -> servicio.cambiarEstado(99L, EstadoIncidencia.RESUELTA));
    }

    private Incidencia nuevaIncidencia(Long categoriaId) {
        return new Incidencia(null, "Poste dañado", "No enciende", "Calle 1", null,
                PrioridadIncidencia.ALTA, null, null, new Categoria(categoriaId, null));
    }

    private static class FakeIncidenciaRepository implements IncidenciaRepositoryPort {

        private final Map<Long, Incidencia> datos = new HashMap<>();
        private long siguienteId = 1;

        @Override
        public Incidencia guardar(Incidencia incidencia) {
            if (incidencia.getId() == null) {
                incidencia.setId(siguienteId++);
            }
            datos.put(incidencia.getId(), incidencia);
            return incidencia;
        }

        @Override
        public Optional<Incidencia> buscarPorId(Long id) {
            return Optional.ofNullable(datos.get(id));
        }

        @Override
        public List<Incidencia> listar(EstadoIncidencia estado, Long categoriaId) {
            return datos.values().stream()
                    .filter(i -> estado == null || i.getEstado() == estado)
                    .filter(i -> categoriaId == null || i.getCategoria().getId().equals(categoriaId))
                    .toList();
        }

        @Override
        public List<Incidencia> listarPorUsuario(Long usuarioId) {
            return datos.values().stream()
                    .filter(i -> i.getUsuarioReporta().getId().equals(usuarioId))
                    .toList();
        }
    }

    private static class FakeCategoriaRepository implements CategoriaRepositoryPort {

        private final List<Categoria> datos = new ArrayList<>();

        @Override
        public List<Categoria> listarTodas() {
            return List.copyOf(datos);
        }

        @Override
        public Categoria guardar(Categoria categoria) {
            datos.add(categoria);
            return categoria;
        }

        @Override
        public Optional<Categoria> buscarPorId(Long id) {
            return datos.stream().filter(c -> c.getId().equals(id)).findFirst();
        }

        @Override
        public void eliminar(Long id) {
            datos.removeIf(c -> c.getId().equals(id));
        }
    }
}
