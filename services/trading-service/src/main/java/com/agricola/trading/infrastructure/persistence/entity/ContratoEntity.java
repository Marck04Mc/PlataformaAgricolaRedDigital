package com.agricola.trading.infrastructure.persistence.entity;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "contratos")
public class ContratoEntity {
    @Id
    private UUID id;
    private UUID productorId;
    private UUID compradorId;
    private String terminos;
    private String estado;

    @OneToMany(mappedBy = "contrato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoEntity> pedidos;

    public ContratoEntity() {}

    public ContratoEntity(UUID id, UUID productorId, UUID compradorId, String terminos, String estado) {
        this.id = id;
        this.productorId = productorId;
        this.compradorId = compradorId;
        this.terminos = terminos;
        this.estado = estado;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getProductorId() { return productorId; }
    public void setProductorId(UUID productorId) { this.productorId = productorId; }
    public UUID getCompradorId() { return compradorId; }
    public void setCompradorId(UUID compradorId) { this.compradorId = compradorId; }
    public String getTerminos() { return terminos; }
    public void setTerminos(String terminos) { this.terminos = terminos; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public List<PedidoEntity> getPedidos() { return pedidos; }
    public void setPedidos(List<PedidoEntity> pedidos) { this.pedidos = pedidos; }
}
