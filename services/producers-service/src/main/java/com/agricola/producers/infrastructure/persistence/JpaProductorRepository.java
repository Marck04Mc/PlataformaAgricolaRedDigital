package com.agricola.producers.infrastructure.persistence;

import com.agricola.producers.infrastructure.persistence.entity.ProductorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface JpaProductorRepository extends JpaRepository<ProductorEntity, UUID> {
}
