package com.agricola.certifications.infrastructure.persistence.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "certificaciones")
public class CertificacionEntity {
    @Id
    private UUID id;
    private UUID productorId;
    private String tipo;
    private String urlDocumento;
    private String estado;

    public CertificacionEntity() {}

    public CertificacionEntity(UUID id, UUID productorId, String tipo, String urlDocumento, String estado) {
        this.id = id;
        this.productorId = productorId;
        this.tipo = tipo;
        this.urlDocumento = urlDocumento;
        this.estado = estado;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getProductorId() { return productorId; }
    public void setProductorId(UUID productorId) { this.productorId = productorId; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getUrlDocumento() { return urlDocumento; }
    public void setUrlDocumento(String urlDocumento) { this.urlDocumento = urlDocumento; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
