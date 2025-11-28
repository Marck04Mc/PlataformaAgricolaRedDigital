package com.agricola.logistics.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Envio {
    private String id;
    private UUID pedidoId;
    private UUID transportistaId;
    private String estado;
    private List<String> historialUbicacion;

    public Envio(String id, UUID pedidoId, UUID transportistaId) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.transportistaId = transportistaId;
        this.estado = "PENDIENTE";
        this.historialUbicacion = new ArrayList<>();
    }

    public Envio(String id, UUID pedidoId, UUID transportistaId, String estado, List<String> historialUbicacion) {
        this.id = id;
        this.pedidoId = pedidoId;
        this.transportistaId = transportistaId;
        this.estado = estado;
        this.historialUbicacion = historialUbicacion;
    }

    public void actualizarUbicacion(String ubicacion) {
        this.historialUbicacion.add(ubicacion);
    }

    public void cambiarEstado(String nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public String getId() {
        return id;
    }

    public UUID getPedidoId() {
        return pedidoId;
    }

    public UUID getTransportistaId() {
        return transportistaId;
    }

    public String getEstado() {
        return estado;
    }

    public List<String> getHistorialUbicacion() {
        return historialUbicacion;
    }
}
