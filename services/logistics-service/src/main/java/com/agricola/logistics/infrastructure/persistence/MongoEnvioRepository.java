package com.agricola.logistics.infrastructure.persistence;

import com.agricola.logistics.infrastructure.persistence.document.EnvioDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoEnvioRepository extends MongoRepository<EnvioDocument, String> {
}
