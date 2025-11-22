package com.agricola.producers.infrastructure.persistence;

import com.agricola.producers.domain.model.Productor;
import com.agricola.producers.domain.repository.ProductorRepository;
import com.agricola.producers.infrastructure.persistence.entity.ProductorEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class PostgresProductorRepository implements ProductorRepository {

    private final JpaProductorRepository jpaRepository;

    public PostgresProductorRepository(JpaProductorRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Productor productor) {
        ProductorEntity entity = new ProductorEntity();
        entity.setId(productor.getId());
        entity.setNombre(productor.getNombre());
        entity.setIdentificacion(productor.getIdentificacion());
        jpaRepository.save(entity);
    }

    @Override
    public Optional<Productor> findById(UUID id) {
        return jpaRepository.findById(id)
            .map(entity -> new Productor(entity.getId(), entity.getNombre(), entity.getIdentificacion()));
    }

    @Override
    public List<Productor> findAll() {
        return jpaRepository.findAll().stream()
            .map(entity -> new Productor(entity.getId(), entity.getNombre(), entity.getIdentificacion()))
            .collect(Collectors.toList());
    }
}
