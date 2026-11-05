package com.didier.incidencias_backend.infrastructure.adapter.out.persistence;

import com.didier.incidencias_backend.domain.model.EstadoIncidencia;
import com.didier.incidencias_backend.domain.model.PrioridadIncidencia;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "incidencias")
public class IncidenciaJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = false)
    private String ubicacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoIncidencia estado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PrioridadIncidencia prioridad;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_reporta_id", nullable = false)
    private UsuarioJpaEntity usuarioReporta;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    private CategoriaJpaEntity categoria;

    public IncidenciaJpaEntity() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public EstadoIncidencia getEstado() { return estado; }
    public void setEstado(EstadoIncidencia estado) { this.estado = estado; }

    public PrioridadIncidencia getPrioridad() { return prioridad; }
    public void setPrioridad(PrioridadIncidencia prioridad) { this.prioridad = prioridad; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public UsuarioJpaEntity getUsuarioReporta() { return usuarioReporta; }
    public void setUsuarioReporta(UsuarioJpaEntity usuarioReporta) { this.usuarioReporta = usuarioReporta; }

    public CategoriaJpaEntity getCategoria() { return categoria; }
    public void setCategoria(CategoriaJpaEntity categoria) { this.categoria = categoria; }
}
