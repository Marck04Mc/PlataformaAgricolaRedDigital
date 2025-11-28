package com.agricola.payments.domain.model;

import com.agricola.shared.domain.AggregateRoot;
import java.util.UUID;

public class Transaccion extends AggregateRoot<UUID> {
    private UUID contratoId;
    private double monto;
    private String estado; // PENDIENTE, COMPLETADO, FALLIDO
    private String metodoPago;

    public Transaccion(UUID id, UUID contratoId, double monto, String estado, String metodoPago) {
        super(id);
        this.contratoId = contratoId;
        this.monto = monto;
        this.estado = estado;
        this.metodoPago = metodoPago;
    }

    // Getters
    public UUID getContratoId() {
        return contratoId;
    }

    public double getMonto() {
        return monto;
    }

    public String getEstado() {
        return estado;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void cambiarEstado(String nuevoEstado) {
        this.estado = nuevoEstado;
    }
}
