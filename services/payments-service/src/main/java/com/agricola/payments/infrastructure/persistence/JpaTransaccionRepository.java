package com.agricola.payments.infrastructure.persistence;

import com.agricola.payments.infrastructure.persistence.entity.TransaccionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface JpaTransaccionRepository extends JpaRepository<TransaccionEntity, UUID> {
}
