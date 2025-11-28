package com.agricola.trading.domain.repository;

import com.agricola.trading.domain.model.Contrato;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContratoRepository {
    void save(Contrato contrato);

    Optional<Contrato> findById(UUID id);

    List<Contrato> findAll();
}
