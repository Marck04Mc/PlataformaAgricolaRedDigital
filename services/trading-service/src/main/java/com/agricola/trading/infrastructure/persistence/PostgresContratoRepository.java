package com.agricola.trading.infrastructure.persistence;

import com.agricola.trading.domain.model.Contrato;
import com.agricola.trading.domain.repository.ContratoRepository;
import com.agricola.trading.infrastructure.persistence.entity.ContratoEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class PostgresContratoRepository implements ContratoRepository {

    private final JpaContratoRepository jpaRepository;

    public PostgresContratoRepository(JpaContratoRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Contrato contrato) {
        ContratoEntity entity = new ContratoEntity(
            contrato.getId(),
            contrato.getProductorId(),
            contrato.getCompradorId(),
            contrato.getTerminos(),
            contrato.getEstado()
        );
        jpaRepository.save(entity);
    }

    @Override
    public Optional<Contrato> findById(UUID id) {
        return jpaRepository.findById(id)
            .map(entity -> new Contrato(
                entity.getId(),
                entity.getProductorId(),
                entity.getCompradorId(),
                entity.getTerminos()
            ));
    }
}
