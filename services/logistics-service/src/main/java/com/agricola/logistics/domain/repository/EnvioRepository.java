package com.agricola.logistics.domain.repository;

import com.agricola.logistics.domain.model.Envio;
import java.util.Optional;
import java.util.UUID;

public interface EnvioRepository {
    void save(Envio envio);
    Optional<Envio> findById(UUID id);
}
