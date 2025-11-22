package com.agricola.inventory.application;

import com.agricola.inventory.domain.model.ProductoAgricola;
import com.agricola.inventory.domain.repository.ProductoAgricolaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class RegistrarProductoUseCase {

    private final ProductoAgricolaRepository repository;

    public RegistrarProductoUseCase(ProductoAgricolaRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void ejecutar(String nombre, String descripcion, double precio, double cantidad, UUID productorId) {
        ProductoAgricola producto = new ProductoAgricola(UUID.randomUUID(), nombre, descripcion, precio, cantidad, productorId);
        repository.save(producto);
    }
}
