package com.agricola.inventory.infrastructure.persistence;

import com.agricola.inventory.domain.model.ProductoAgricola;
import com.agricola.inventory.domain.repository.ProductoAgricolaRepository;
import com.agricola.inventory.infrastructure.persistence.entity.ProductoAgricolaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class PostgresProductoAgricolaRepository implements ProductoAgricolaRepository {

    private final JpaProductoAgricolaRepository jpaRepository;

    public PostgresProductoAgricolaRepository(JpaProductoAgricolaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(ProductoAgricola producto) {
        ProductoAgricolaEntity entity = new ProductoAgricolaEntity(
                producto.getId(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getImagenUrl(),
                producto.getPrecio(),
                producto.getCantidadDisponible(),
                producto.getProductorId());
        jpaRepository.save(entity);
    }

    @Override
    public Optional<ProductoAgricola> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(entity -> new ProductoAgricola(
                        entity.getId(),
                        entity.getNombre(),
                        entity.getDescripcion(),
                        entity.getImagenUrl(),
                        entity.getPrecio(),
                        entity.getCantidadDisponible(),
                        entity.getProductorId()));
    }

    @Override
    public List<ProductoAgricola> findAll() {
        return jpaRepository.findAll().stream()
                .map(entity -> new ProductoAgricola(
                        entity.getId(),
                        entity.getNombre(),
                        entity.getDescripcion(),
                        entity.getImagenUrl(),
                        entity.getPrecio(),
                        entity.getCantidadDisponible(),
                        entity.getProductorId()))
                .collect(Collectors.toList());
    }
}
