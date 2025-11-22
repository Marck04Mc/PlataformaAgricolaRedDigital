package com.agricola.payments.infrastructure.persistence.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "transacciones")
public class TransaccionEntity {
    @Id
    private UUID id;
    private UUID contratoId;
    private double monto;
    private String estado;
    private String metodoPago;

    public TransaccionEntity() {}

    public TransaccionEntity(UUID id, UUID contratoId, double monto, String estado, String metodoPago) {
        this.id = id;
        this.contratoId = contratoId;
        this.monto = monto;
        this.estado = estado;
        this.metodoPago = metodoPago;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getContratoId() { return contratoId; }
    public void setContratoId(UUID contratoId) { this.contratoId = contratoId; }
    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
}
