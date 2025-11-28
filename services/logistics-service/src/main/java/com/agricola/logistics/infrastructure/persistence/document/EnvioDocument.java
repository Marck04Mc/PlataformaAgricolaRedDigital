package com.agricola.logistics.infrastructure.persistence.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document(collection = "envios")
public class EnvioDocument {
    @Id
    private String id;
    private UUID pedidoId;
    private UUID transportistaId;
    private String estado;
    private List<String> historialUbicacion;

    public EnvioDocument() {
    }

    public EnvioDocument(String id, UUID pedidoId, UUID transportistaId, String estado,
            List<String> historialUbicacion) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.transportistaId = transportistaId;
        this.estado = estado;
        this.historialUbicacion = historialUbicacion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UUID getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(UUID pedidoId) {
        this.pedidoId = pedidoId;
    }

    public UUID getTransportistaId() {
        return transportistaId;
    }

    public void setTransportistaId(UUID transportistaId) {
        this.transportistaId = transportistaId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<String> getHistorialUbicacion() {
        return historialUbicacion;
    }

    public void setHistorialUbicacion(List<String> historialUbicacion) {
        this.historialUbicacion = historialUbicacion;
    }
}
