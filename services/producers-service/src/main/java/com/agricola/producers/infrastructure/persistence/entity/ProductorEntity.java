package com.agricola.producers.infrastructure.persistence.entity;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "productores")
public class ProductorEntity {
    @Id
    private UUID id;
    private String nombre;
    private String identificacion;

    @OneToMany(mappedBy = "productor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FincaEntity> fincas;

    // Constructors, Getters, Setters
    public ProductorEntity() {}

    public ProductorEntity(UUID id, String nombre, String identificacion) {
        this.id = id;
        this.nombre = nombre;
        this.identificacion = identificacion;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getIdentificacion() { return identificacion; }
    public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }
    public List<FincaEntity> getFincas() { return fincas; }
    public void setFincas(List<FincaEntity> fincas) { this.fincas = fincas; }
}
