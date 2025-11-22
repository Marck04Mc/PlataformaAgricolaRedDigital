package com.agricola.payments.application;

import com.agricola.payments.domain.model.Transaccion;
import com.agricola.payments.domain.repository.TransaccionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ProcesarPagoUseCase {

    private final TransaccionRepository repository;

    public ProcesarPagoUseCase(TransaccionRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void ejecutar(UUID contratoId, double monto, String metodoPago) {
        Transaccion transaccion = new Transaccion(UUID.randomUUID(), contratoId, monto, "PENDIENTE", metodoPago);
        repository.save(transaccion);
    }
}
