package com.agricola.logistics.infrastructure.persistence.document;

import com.agricola.logistics.domain.model.PuntoControl;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document(collection = "envios")
public class EnvioDocument {
    @Id
    private UUID id;
    private UUID pedidoId;
    private UUID transportistaId;
    private String estado;
    private List<PuntoControl> historialUbicacion;

    public EnvioDocument() {}

    public EnvioDocument(UUID id, UUID pedidoId, UUID transportistaId, String estado, List<PuntoControl> historialUbicacion) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.transportistaId = transportistaId;
        this.estado = estado;
        this.historialUbicacion = historialUbicacion;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getPedidoId() { return pedidoId; }
    public void setPedidoId(UUID pedidoId) { this.pedidoId = pedidoId; }
    public UUID getTransportistaId() { return transportistaId; }
    public void setTransportistaId(UUID transportistaId) { this.transportistaId = transportistaId; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public List<PuntoControl> getHistorialUbicacion() { return historialUbicacion; }
    public void setHistorialUbicacion(List<PuntoControl> historialUbicacion) { this.historialUbicacion = historialUbicacion; }
}
