package com.agricola.producers.domain.repository;

import com.agricola.producers.domain.model.Productor;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductorRepository {
    void save(Productor productor);

    Optional<Productor> findById(UUID id);

    List<Productor> findAll();
}
