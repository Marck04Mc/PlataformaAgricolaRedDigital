package com.agricola.certifications.infrastructure.persistence;

import com.agricola.certifications.infrastructure.persistence.entity.CertificacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface JpaCertificacionRepository extends JpaRepository<CertificacionEntity, UUID> {
}
