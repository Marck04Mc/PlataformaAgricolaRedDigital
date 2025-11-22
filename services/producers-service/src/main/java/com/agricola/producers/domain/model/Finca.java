package com.agricola.producers.domain.model;

import com.agricola.shared.domain.Entity;
import java.util.UUID;

public class Finca extends Entity<UUID> {
    private String nombre;
    private String ubicacion;
    private String tipoCultivo;
    private double hectareas;

    public Finca(UUID id, String nombre, String ubicacion, String tipoCultivo, double hectareas) {
        super(id);
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.tipoCultivo = tipoCultivo;
        this.hectareas = hectareas;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getTipoCultivo() {
        return tipoCultivo;
    }

    public double getHectareas() {
        return hectareas;
    }
}
