package com.agricola.trading.infrastructure.persistence.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "pedidos")
public class PedidoEntity {
    @Id
    private UUID id;
    private double cantidad;
    private double precioUnitario;

    @ManyToOne
    @JoinColumn(name = "contrato_id")
    private ContratoEntity contrato;

    public PedidoEntity() {}

    public PedidoEntity(UUID id, double cantidad, double precioUnitario, ContratoEntity contrato) {
        this.id = id;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.contrato = contrato;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public double getCantidad() { return cantidad; }
    public void setCantidad(double cantidad) { this.cantidad = cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }
    public ContratoEntity getContrato() { return contrato; }
    public void setContrato(ContratoEntity contrato) { this.contrato = contrato; }
}
