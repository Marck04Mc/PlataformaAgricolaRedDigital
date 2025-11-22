package com.agricola.trading.infrastructure.persistence;

import com.agricola.trading.infrastructure.persistence.entity.ContratoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface JpaContratoRepository extends JpaRepository<ContratoEntity, UUID> {
}
