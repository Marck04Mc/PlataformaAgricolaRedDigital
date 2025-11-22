package com.agricola.logistics.domain.model;

import com.agricola.shared.domain.ValueObject;
import java.time.LocalDateTime;

public class PuntoControl implements ValueObject {
    private double latitud;
    private double longitud;
    private double temperatura;
    private LocalDateTime timestamp;

    public PuntoControl(double latitud, double longitud, double temperatura) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.temperatura = temperatura;
        this.timestamp = LocalDateTime.now();
    }

    // Getters
    public double getLatitud() { return latitud; }
    public double getLongitud() { return longitud; }
    public double getTemperatura() { return temperatura; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
