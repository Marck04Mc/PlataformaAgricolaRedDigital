package com.agricola.logistics.application;

import com.agricola.logistics.domain.model.Envio;
import com.agricola.logistics.domain.repository.EnvioRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegistrarEnvioUseCase {

    private final EnvioRepository envioRepository;

    public RegistrarEnvioUseCase(EnvioRepository envioRepository) {
        this.envioRepository = envioRepository;
    }

    public void ejecutar(UUID pedidoId, UUID transportistaId) {
        Envio envio = new Envio(UUID.randomUUID().toString(), pedidoId, transportistaId);
        envioRepository.save(envio);
    }
}
