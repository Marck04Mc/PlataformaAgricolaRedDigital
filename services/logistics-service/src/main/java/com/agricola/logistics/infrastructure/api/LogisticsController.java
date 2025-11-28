package com.agricola.logistics.infrastructure.api;

import com.agricola.logistics.application.RegistrarEnvioUseCase;
import com.agricola.logistics.domain.model.Envio;
import com.agricola.logistics.domain.repository.EnvioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/envios")
public class LogisticsController {

    private final RegistrarEnvioUseCase registrarEnvioUseCase;
    private final EnvioRepository envioRepository;

    public LogisticsController(RegistrarEnvioUseCase registrarEnvioUseCase, EnvioRepository envioRepository) {
        this.registrarEnvioUseCase = registrarEnvioUseCase;
        this.envioRepository = envioRepository;
    }

    @GetMapping
    public ResponseEntity<List<EnvioResponse>> listar() {
        List<EnvioResponse> envios = envioRepository.findAll().stream()
                .map(e -> new EnvioResponse(e.getId(), e.getPedidoId(), e.getTransportistaId(), e.getEstado()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(envios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnvioResponse> obtener(@PathVariable String id) {
        return envioRepository.findById(id)
                .map(e -> ResponseEntity
                        .ok(new EnvioResponse(e.getId(), e.getPedidoId(), e.getTransportistaId(), e.getEstado())))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> registrar(@RequestBody RegistrarEnvioRequest request) {
        registrarEnvioUseCase.ejecutar(request.getPedidoId(), request.getTransportistaId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarEstado(@PathVariable String id,
            @RequestBody ActualizarEstadoRequest request) {
        return envioRepository.findById(id)
                .map(envio -> {
                    envio.cambiarEstado(request.getEstado());
                    envioRepository.save(envio);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    public static class ActualizarEstadoRequest {
        private String estado;

        public String getEstado() {
            return estado;
        }

        public void setEstado(String estado) {
            this.estado = estado;
        }
    }

    public static class RegistrarEnvioRequest {
        private UUID pedidoId;
        private UUID transportistaId;

        public UUID getPedidoId() {
            return pedidoId;
        }

        public void setPedidoId(UUID pedidoId) {
            this.pedidoId = pedidoId;
        }

        public UUID getTransportistaId() {
            return transportistaId;
        }

        public void setTransportistaId(UUID transportistaId) {
            this.transportistaId = transportistaId;
        }
    }

    public static class EnvioResponse {
        private String id;
        private UUID pedidoId;
        private UUID transportistaId;
        private String estado;

        public EnvioResponse(String id, UUID pedidoId, UUID transportistaId, String estado) {
            this.id = id;
            this.pedidoId = pedidoId;
            this.transportistaId = transportistaId;
            this.estado = estado;
        }

        public String getId() {
            return id;
        }

        public UUID getPedidoId() {
            return pedidoId;
        }

        public UUID getTransportistaId() {
            return transportistaId;
        }

        public String getEstado() {
            return estado;
        }
    }
}
