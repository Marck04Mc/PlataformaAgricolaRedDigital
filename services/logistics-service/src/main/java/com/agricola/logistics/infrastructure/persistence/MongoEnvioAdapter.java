package com.agricola.logistics.infrastructure.persistence;

import com.agricola.logistics.domain.model.Envio;
import com.agricola.logistics.domain.repository.EnvioRepository;
import com.agricola.logistics.infrastructure.persistence.document.EnvioDocument;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class MongoEnvioAdapter implements EnvioRepository {

    private final MongoEnvioRepository mongoRepository;

    public MongoEnvioAdapter(MongoEnvioRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    @Override
    public void save(Envio envio) {
        EnvioDocument document = new EnvioDocument(
            envio.getId(),
            envio.getPedidoId(),
            envio.getTransportistaId(),
            envio.getEstado(),
            envio.getHistorialUbicacion()
        );
        mongoRepository.save(document);
    }

    @Override
    public Optional<Envio> findById(UUID id) {
        return mongoRepository.findById(id)
            .map(document -> {
                Envio envio = new Envio(document.getId(), document.getPedidoId(), document.getTransportistaId());
                // Restore state if needed, simplified for now
                return envio;
            });
    }
}
