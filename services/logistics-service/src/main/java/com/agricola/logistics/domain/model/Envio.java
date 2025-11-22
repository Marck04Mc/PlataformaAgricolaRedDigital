package com.agricola.logistics.domain.model;

import com.agricola.shared.domain.AggregateRoot;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Envio extends AggregateRoot<UUID> {
    private UUID pedidoId;
    private UUID transportistaId;
    private String estado; // PENDIENTE, EN_TRANSITO, ENTREGADO
    private List<PuntoControl> historialUbicacion;

    public Envio(UUID id, UUID pedidoId, UUID transportistaId) {
        super(id);
        this.pedidoId = pedidoId;
        this.transportistaId = transportistaId;
        this.estado = "PENDIENTE";
        this.historialUbicacion = new ArrayList<>();
    }

    public void registrarUbicacion(double latitud, double longitud, double temperatura) {
        this.historialUbicacion.add(new PuntoControl(latitud, longitud, temperatura));
    }

    // Getters
    public UUID getPedidoId() { return pedidoId; }
    public UUID getTransportistaId() { return transportistaId; }
    public String getEstado() { return estado; }
    public List<PuntoControl> getHistorialUbicacion() { return new ArrayList<>(historialUbicacion); }
}
