package com.agricola.inventory.infrastructure.api;

import com.agricola.inventory.application.RegistrarProductoUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/productos")
public class InventoryController {

    private final RegistrarProductoUseCase registrarProductoUseCase;

    public InventoryController(RegistrarProductoUseCase registrarProductoUseCase) {
        this.registrarProductoUseCase = registrarProductoUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> registrar(@RequestBody RegistrarProductoRequest request) {
        registrarProductoUseCase.ejecutar(request.getNombre(), request.getDescripcion(), request.getPrecio(), request.getCantidad(), request.getProductorId());
        return ResponseEntity.ok().build();
    }

    public static class RegistrarProductoRequest {
        private String nombre;
        private String descripcion;
        private double precio;
        private double cantidad;
        private UUID productorId;

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
        public double getPrecio() { return precio; }
        public void setPrecio(double precio) { this.precio = precio; }
        public double getCantidad() { return cantidad; }
        public void setCantidad(double cantidad) { this.cantidad = cantidad; }
        public UUID getProductorId() { return productorId; }
        public void setProductorId(UUID productorId) { this.productorId = productorId; }
    }
}
