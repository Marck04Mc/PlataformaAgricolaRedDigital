package com.agricola.certifications.domain.repository;

import com.agricola.certifications.domain.model.Certificacion;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CertificacionRepository {
    void save(Certificacion certificacion);

    Optional<Certificacion> findById(UUID id);

    List<Certificacion> findAll();
}
