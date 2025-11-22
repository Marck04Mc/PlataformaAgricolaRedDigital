package com.agricola.producers.infrastructure.api;

import com.agricola.producers.application.RegistrarProductorUseCase;
import com.agricola.producers.domain.model.Productor;
import com.agricola.producers.domain.repository.ProductorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productores")
public class ProductorController {

    private final RegistrarProductorUseCase registrarProductorUseCase;
    private final ProductorRepository productorRepository;

    public ProductorController(RegistrarProductorUseCase registrarProductorUseCase, ProductorRepository productorRepository) {
        this.registrarProductorUseCase = registrarProductorUseCase;
        this.productorRepository = productorRepository;
    }

    @GetMapping
    public ResponseEntity<List<ProductorResponse>> listar() {
        // Simple implementation - in production, use a proper query service
        return ResponseEntity.ok(productorRepository.findAll().stream()
            .map(p -> new ProductorResponse(p.getId(), p.getNombre(), p.getIdentificacion()))
            .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductorResponse> obtener(@PathVariable UUID id) {
        return productorRepository.findById(id)
            .map(p -> ResponseEntity.ok(new ProductorResponse(p.getId(), p.getNombre(), p.getIdentificacion())))
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> registrar(@RequestBody RegistrarProductorRequest request) {
        registrarProductorUseCase.ejecutar(request.getNombre(), request.getIdentificacion());
        return ResponseEntity.ok().build();
    }

    public static class RegistrarProductorRequest {
        private String nombre;
        private String identificacion;

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
        public String getIdentificacion() { return identificacion; }
        public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }
    }

    public static class ProductorResponse {
        private UUID id;
        private String nombre;
        private String identificacion;

        public ProductorResponse(UUID id, String nombre, String identificacion) {
            this.id = id;
            this.nombre = nombre;
            this.identificacion = identificacion;
        }

        public UUID getId() { return id; }
        public String getNombre() { return nombre; }
        public String getIdentificacion() { return identificacion; }
    }
}
