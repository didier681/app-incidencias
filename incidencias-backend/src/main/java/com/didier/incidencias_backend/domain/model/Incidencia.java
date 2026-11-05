package com.didier.incidencias_backend.domain.model;

import java.time.LocalDateTime;

public class Incidencia {

    private Long id;
    private String titulo;
    private String descripcion;
    private String ubicacion;
    private EstadoIncidencia estado;
    private PrioridadIncidencia prioridad;
    private LocalDateTime fechaCreacion;
    private Usuario usuarioReporta;
    private Categoria categoria;

    public Incidencia() {
    }

    public Incidencia(Long id, String titulo, String descripcion, String ubicacion,
                      EstadoIncidencia estado, PrioridadIncidencia prioridad, LocalDateTime fechaCreacion,
                      Usuario usuarioReporta, Categoria categoria) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.estado = estado;
        this.prioridad = prioridad;
        this.fechaCreacion = fechaCreacion;
        this.usuarioReporta = usuarioReporta;
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public EstadoIncidencia getEstado() {
        return estado;
    }

    public void setEstado(EstadoIncidencia estado) {
        this.estado = estado;
    }

    public PrioridadIncidencia getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(PrioridadIncidencia prioridad) {
        this.prioridad = prioridad;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Usuario getUsuarioReporta() {
        return usuarioReporta;
    }

    public void setUsuarioReporta(Usuario usuarioReporta) {
        this.usuarioReporta = usuarioReporta;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
