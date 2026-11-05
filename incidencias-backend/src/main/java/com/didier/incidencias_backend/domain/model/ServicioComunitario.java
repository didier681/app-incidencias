package com.didier.incidencias_backend.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ServicioComunitario {

    private Long id;
    private String tipo;
    private String descripcion;
    private LocalDate fechaSolicitada;
    private EstadoServicio estado;
    private LocalDateTime fechaCreacion;
    private Usuario usuario;

    public ServicioComunitario() {
    }

    public ServicioComunitario(Long id, String tipo, String descripcion, LocalDate fechaSolicitada,
                               EstadoServicio estado, LocalDateTime fechaCreacion, Usuario usuario) {
        this.id = id;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fechaSolicitada = fechaSolicitada;
        this.estado = estado;
        this.fechaCreacion = fechaCreacion;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaSolicitada() {
        return fechaSolicitada;
    }

    public void setFechaSolicitada(LocalDate fechaSolicitada) {
        this.fechaSolicitada = fechaSolicitada;
    }

    public EstadoServicio getEstado() {
        return estado;
    }

    public void setEstado(EstadoServicio estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
