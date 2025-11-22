package com.agricola.certifications.infrastructure.persistence;

import com.agricola.certifications.domain.model.Certificacion;
import com.agricola.certifications.domain.repository.CertificacionRepository;
import com.agricola.certifications.infrastructure.persistence.entity.CertificacionEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class PostgresCertificacionRepository implements CertificacionRepository {

    private final JpaCertificacionRepository jpaRepository;

    public PostgresCertificacionRepository(JpaCertificacionRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Certificacion certificacion) {
        CertificacionEntity entity = new CertificacionEntity(
            certificacion.getId(),
            certificacion.getProductorId(),
            certificacion.getTipo(),
            certificacion.getUrlDocumento(),
            certificacion.getEstado()
        );
        jpaRepository.save(entity);
    }

    @Override
    public Optional<Certificacion> findById(UUID id) {
        return jpaRepository.findById(id)
            .map(entity -> new Certificacion(
                entity.getId(),
                entity.getProductorId(),
                entity.getTipo(),
                entity.getUrlDocumento(),
                entity.getEstado()
            ));
    }
}
