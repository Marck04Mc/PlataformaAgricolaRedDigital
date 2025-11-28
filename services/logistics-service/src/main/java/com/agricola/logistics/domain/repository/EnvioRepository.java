package com.agricola.logistics.domain.repository;

import com.agricola.logistics.domain.model.Envio;
import java.util.List;
import java.util.Optional;

public interface EnvioRepository {
    void save(Envio envio);

    Optional<Envio> findById(String id);

    List<Envio> findAll();
}
