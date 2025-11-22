package com.agricola.trading.application;

import com.agricola.trading.domain.model.Contrato;
import com.agricola.trading.domain.repository.ContratoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CrearContratoUseCase {

    private final ContratoRepository repository;

    public CrearContratoUseCase(ContratoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void ejecutar(UUID productorId, UUID compradorId, String terminos) {
        Contrato contrato = new Contrato(UUID.randomUUID(), productorId, compradorId, terminos);
        repository.save(contrato);
    }
}
