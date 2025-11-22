package com.agricola.payments.domain.repository;

import com.agricola.payments.domain.model.Transaccion;
import java.util.Optional;
import java.util.UUID;

public interface TransaccionRepository {
    void save(Transaccion transaccion);
    Optional<Transaccion> findById(UUID id);
}
