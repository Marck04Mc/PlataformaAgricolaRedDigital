package com.agricola.certifications.application;

import com.agricola.certifications.domain.model.Certificacion;
import com.agricola.certifications.domain.repository.CertificacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class RegistrarCertificacionUseCase {

    private final CertificacionRepository repository;

    public RegistrarCertificacionUseCase(CertificacionRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void ejecutar(UUID productorId, String tipo, String urlDocumento) {
        Certificacion certificacion = new Certificacion(UUID.randomUUID(), productorId, tipo, urlDocumento, "PENDIENTE");
        repository.save(certificacion);
    }
}
