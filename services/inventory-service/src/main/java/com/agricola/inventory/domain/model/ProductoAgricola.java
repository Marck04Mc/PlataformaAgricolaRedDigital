package com.agricola.inventory.domain.model;

import com.agricola.shared.domain.AggregateRoot;
import java.util.UUID;

public class ProductoAgricola extends AggregateRoot<UUID> {
    private String nombre;
    private String descripcion;
    private String imagenUrl;
    private double precio;
    private double cantidadDisponible;
    private UUID productorId;

    public ProductoAgricola(UUID id, String nombre, String descripcion, String imagenUrl, double precio,
            double cantidadDisponible, UUID productorId) {
        super(id);
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagenUrl = imagenUrl;
        this.precio = precio;
        this.cantidadDisponible = cantidadDisponible;
        this.productorId = productorId;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public double getPrecio() {
        return precio;
    }

    public double getCantidadDisponible() {
        return cantidadDisponible;
    }

    public UUID getProductorId() {
        return productorId;
    }
}
