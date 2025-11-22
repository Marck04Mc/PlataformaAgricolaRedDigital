package com.agricola.inventory.infrastructure.persistence;

import com.agricola.inventory.infrastructure.persistence.entity.ProductoAgricolaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface JpaProductoAgricolaRepository extends JpaRepository<ProductoAgricolaEntity, UUID> {
}
