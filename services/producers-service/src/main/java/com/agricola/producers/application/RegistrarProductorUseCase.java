package com.agricola.producers.application;

import com.agricola.producers.domain.model.Productor;
import com.agricola.producers.domain.repository.ProductorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class RegistrarProductorUseCase {

    private final ProductorRepository repository;

    public RegistrarProductorUseCase(ProductorRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void ejecutar(String nombre, String identificacion) {
        Productor productor = new Productor(UUID.randomUUID(), nombre, identificacion);
        repository.save(productor);
    }
}
