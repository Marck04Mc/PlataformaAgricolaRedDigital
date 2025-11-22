package com.agricola.certifications.domain.model;

import com.agricola.shared.domain.AggregateRoot;
import java.util.UUID;

public class Certificacion extends AggregateRoot<UUID> {
    private UUID productorId;
    private String tipo; // ORGANICO, COMERCIO_JUSTO, ETC
    private String urlDocumento; // URL en S3/MinIO
    private String estado; // VALIDADO, PENDIENTE

    public Certificacion(UUID id, UUID productorId, String tipo, String urlDocumento, String estado) {
        super(id);
        this.productorId = productorId;
        this.tipo = tipo;
        this.urlDocumento = urlDocumento;
        this.estado = estado;
    }

    // Getters
    public UUID getProductorId() { return productorId; }
    public String getTipo() { return tipo; }
    public String getUrlDocumento() { return urlDocumento; }
    public String getEstado() { return estado; }
}
