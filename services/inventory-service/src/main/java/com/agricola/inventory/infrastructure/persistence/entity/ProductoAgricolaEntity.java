package com.agricola.inventory.infrastructure.persistence.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "productos_agricolas")
public class ProductoAgricolaEntity {
    @Id
    private UUID id;
    private String nombre;
    private String descripcion;
    private String imagenUrl;
    private double precio;
    private double cantidadDisponible;
    private UUID productorId;

    public ProductoAgricolaEntity() {
    }

    public ProductoAgricolaEntity(UUID id, String nombre, String descripcion, String imagenUrl, double precio,
            double cantidadDisponible, UUID productorId) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagenUrl = imagenUrl;
        this.precio = precio;
        this.cantidadDisponible = cantidadDisponible;
        this.productorId = productorId;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(double cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public UUID getProductorId() {
        return productorId;
    }

    public void setProductorId(UUID productorId) {
        this.productorId = productorId;
    }
}
