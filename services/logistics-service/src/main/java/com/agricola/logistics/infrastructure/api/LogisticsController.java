package com.agricola.logistics.infrastructure.api;

import com.agricola.logistics.application.RegistrarEnvioUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/envios")
public class LogisticsController {

    private final RegistrarEnvioUseCase registrarEnvioUseCase;

    public LogisticsController(RegistrarEnvioUseCase registrarEnvioUseCase) {
        this.registrarEnvioUseCase = registrarEnvioUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> registrar(@RequestBody RegistrarEnvioRequest request) {
        registrarEnvioUseCase.ejecutar(request.getPedidoId(), request.getTransportistaId());
        return ResponseEntity.ok().build();
    }

    public static class RegistrarEnvioRequest {
        private UUID pedidoId;
        private UUID transportistaId;

        public UUID getPedidoId() { return pedidoId; }
        public void setPedidoId(UUID pedidoId) { this.pedidoId = pedidoId; }
        public UUID getTransportistaId() { return transportistaId; }
        public void setTransportistaId(UUID transportistaId) { this.transportistaId = transportistaId; }
    }
}
