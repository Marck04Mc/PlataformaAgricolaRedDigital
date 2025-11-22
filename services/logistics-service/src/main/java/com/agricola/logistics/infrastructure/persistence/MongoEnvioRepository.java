package com.agricola.logistics.infrastructure.persistence;

import com.agricola.logistics.infrastructure.persistence.document.EnvioDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.UUID;

public interface MongoEnvioRepository extends MongoRepository<EnvioDocument, UUID> {
}
