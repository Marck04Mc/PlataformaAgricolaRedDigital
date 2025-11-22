package com.agricola.producers.infrastructure.persistence.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "fincas")
public class FincaEntity {
    @Id
    private UUID id;
    private String nombre;
    private String ubicacion;
    private String tipoCultivo;
    private double hectareas;

    @ManyToOne
    @JoinColumn(name = "productor_id")
    private ProductorEntity productor;

    // Constructors, Getters, Setters
    public FincaEntity() {}

    public FincaEntity(UUID id, String nombre, String ubicacion, String tipoCultivo, double hectareas, ProductorEntity productor) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.tipoCultivo = tipoCultivo;
        this.hectareas = hectareas;
        this.productor = productor;
    }

    // Getters and Setters...
}
