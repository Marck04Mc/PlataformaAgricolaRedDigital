package com.agricola.inventory.infrastructure.api;

import com.agricola.inventory.application.RegistrarProductoUseCase;
import com.agricola.inventory.domain.model.ProductoAgricola;
import com.agricola.inventory.domain.repository.ProductoAgricolaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productos")
public class InventoryController {

    private final RegistrarProductoUseCase registrarProductoUseCase;
    private final ProductoAgricolaRepository productoRepository;

    public InventoryController(RegistrarProductoUseCase registrarProductoUseCase,
            ProductoAgricolaRepository productoRepository) {
        this.registrarProductoUseCase = registrarProductoUseCase;
        this.productoRepository = productoRepository;
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponse>> listar() {
        List<ProductoResponse> productos = productoRepository.findAll().stream()
                .map(p -> new ProductoResponse(p.getId(), p.getNombre(), p.getDescripcion(), p.getImagenUrl(),
                        p.getPrecio(),
                        p.getCantidadDisponible(), p.getProductorId()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> obtener(@PathVariable UUID id) {
        return productoRepository.findById(id)
                .map(p -> ResponseEntity.ok(new ProductoResponse(p.getId(), p.getNombre(), p.getDescripcion(),
                        p.getImagenUrl(), p.getPrecio(), p.getCantidadDisponible(), p.getProductorId())))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> registrar(@RequestBody RegistrarProductoRequest request) {
        registrarProductoUseCase.ejecutar(
                request.getNombre(),
                request.getDescripcion(),
                request.getImagenUrl(),
                request.getPrecio(),
                request.getCantidadDisponible(),
                request.getProductorId());
        return ResponseEntity.ok().build();
    }

    public static class RegistrarProductoRequest {
        private String nombre;
        private String descripcion;
        private String imagenUrl;
        private double precio;
        private double cantidadDisponible;
        private UUID productorId;

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getImagenUrl() {
            return imagenUrl;
        }

        public void setImagenUrl(String imagenUrl) {
            this.imagenUrl = imagenUrl;
        }

        public double getPrecio() {
            return precio;
        }

        public void setPrecio(double precio) {
            this.precio = precio;
        }

        public double getCantidadDisponible() {
            return cantidadDisponible;
        }

        public void setCantidadDisponible(double cantidadDisponible) {
            this.cantidadDisponible = cantidadDisponible;
        }

        public UUID getProductorId() {
            return productorId;
        }

        public void setProductorId(UUID productorId) {
            this.productorId = productorId;
        }
    }

    public static class ProductoResponse {
        private UUID id;
        private String nombre;
        private String descripcion;
        private String imagenUrl;
        private double precio;
        private double cantidadDisponible;
        private UUID productorId;

        public ProductoResponse(UUID id, String nombre, String descripcion, String imagenUrl, double precio,
                double cantidadDisponible,
                UUID productorId) {
            this.id = id;
            this.nombre = nombre;
            this.descripcion = descripcion;
            this.imagenUrl = imagenUrl;
            this.precio = precio;
            this.cantidadDisponible = cantidadDisponible;
            this.productorId = productorId;
        }

        public UUID getId() {
            return id;
        }

        public String getNombre() {
            return nombre;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public String getImagenUrl() {
            return imagenUrl;
        }

        public double getPrecio() {
            return precio;
        }

        public double getCantidadDisponible() {
            return cantidadDisponible;
        }

        public UUID getProductorId() {
            return productorId;
        }
    }
}
