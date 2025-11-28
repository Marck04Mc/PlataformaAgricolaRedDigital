package com.agricola.inventory.domain.repository;

import com.agricola.inventory.domain.model.ProductoAgricola;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductoAgricolaRepository {
    void save(ProductoAgricola producto);

    Optional<ProductoAgricola> findById(UUID id);

    List<ProductoAgricola> findAll();
}
