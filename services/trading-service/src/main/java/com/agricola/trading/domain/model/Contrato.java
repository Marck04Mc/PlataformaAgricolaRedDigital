package com.agricola.trading.domain.model;

import com.agricola.shared.domain.AggregateRoot;
import java.util.UUID;

public class Contrato extends AggregateRoot<UUID> {
    private UUID productorId;
    private UUID compradorId;
    private String terminos;
    private String estado; // NEGOCIACION, ACEPTADO, RECHAZADO

    public Contrato(UUID id, UUID productorId, UUID compradorId, String terminos) {
        super(id);
        this.productorId = productorId;
        this.compradorId = compradorId;
        this.terminos = terminos;
        this.estado = "NEGOCIACION";
    }

    public void aceptar() {
        this.estado = "ACEPTADO";
    }

    public void rechazar() {
        this.estado = "RECHAZADO";
    }

    // Getters
    public UUID getProductorId() { return productorId; }
    public UUID getCompradorId() { return compradorId; }
    public String getTerminos() { return terminos; }
    public String getEstado() { return estado; }
}
