package com.agricola.trading.domain.model;

import com.agricola.shared.domain.Entity;
import java.util.UUID;

public class Pedido extends Entity<UUID> {
    private UUID contratoId;
    private double cantidad;
    private double precioUnitario;

    public Pedido(UUID id, UUID contratoId, double cantidad, double precioUnitario) {
        super(id);
        this.contratoId = contratoId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    // Getters
    public UUID getContratoId() { return contratoId; }
    public double getCantidad() { return cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
}
