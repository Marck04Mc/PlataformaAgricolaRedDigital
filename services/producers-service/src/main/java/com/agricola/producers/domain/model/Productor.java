package com.agricola.producers.domain.model;

import com.agricola.shared.domain.AggregateRoot;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Productor extends AggregateRoot<UUID> {
    private String nombre;
    private String identificacion;
    private List<Finca> fincas;

    public Productor(UUID id, String nombre, String identificacion) {
        super(id);
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.fincas = new ArrayList<>();
    }

    public void agregarFinca(Finca finca) {
        this.fincas.add(finca);
    }

    public List<Finca> getFincas() {
        return new ArrayList<>(fincas);
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }
}
