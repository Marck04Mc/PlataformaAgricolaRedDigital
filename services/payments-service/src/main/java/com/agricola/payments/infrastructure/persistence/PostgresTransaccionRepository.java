package com.agricola.payments.infrastructure.persistence;

import com.agricola.payments.domain.model.Transaccion;
import com.agricola.payments.domain.repository.TransaccionRepository;
import com.agricola.payments.infrastructure.persistence.entity.TransaccionEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class PostgresTransaccionRepository implements TransaccionRepository {

    private final JpaTransaccionRepository jpaRepository;

    public PostgresTransaccionRepository(JpaTransaccionRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Transaccion transaccion) {
        TransaccionEntity entity = new TransaccionEntity(
            transaccion.getId(),
            transaccion.getContratoId(),
            transaccion.getMonto(),
            transaccion.getEstado(),
            transaccion.getMetodoPago()
        );
        jpaRepository.save(entity);
    }

    @Override
    public Optional<Transaccion> findById(UUID id) {
        return jpaRepository.findById(id)
            .map(entity -> new Transaccion(
                entity.getId(),
                entity.getContratoId(),
                entity.getMonto(),
                entity.getEstado(),
                entity.getMetodoPago()
            ));
    }
}
