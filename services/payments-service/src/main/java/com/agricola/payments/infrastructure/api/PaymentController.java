package com.agricola.payments.infrastructure.api;

import com.agricola.payments.application.ProcesarPagoUseCase;
import com.agricola.payments.domain.model.Transaccion;
import com.agricola.payments.domain.repository.TransaccionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pagos")
public class PaymentController {

    private final ProcesarPagoUseCase procesarPagoUseCase;
    private final TransaccionRepository transaccionRepository;

    public PaymentController(ProcesarPagoUseCase procesarPagoUseCase, TransaccionRepository transaccionRepository) {
        this.procesarPagoUseCase = procesarPagoUseCase;
        this.transaccionRepository = transaccionRepository;
    }

    @GetMapping
    public ResponseEntity<List<TransaccionResponse>> listar() {
        List<TransaccionResponse> transacciones = transaccionRepository.findAll().stream()
                .map(t -> new TransaccionResponse(t.getId(), t.getContratoId(), t.getMonto(), t.getMetodoPago(),
                        t.getEstado()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(transacciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransaccionResponse> obtener(@PathVariable UUID id) {
        return transaccionRepository.findById(id)
                .map(t -> ResponseEntity.ok(new TransaccionResponse(t.getId(), t.getContratoId(), t.getMonto(),
                        t.getMetodoPago(), t.getEstado())))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> procesar(@RequestBody ProcesarPagoRequest request) {
        procesarPagoUseCase.ejecutar(request.getContratoId(), request.getMonto(), request.getMetodoPago());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarEstado(@PathVariable UUID id, @RequestBody ActualizarEstadoRequest request) {
        return transaccionRepository.findById(id)
                .map(transaccion -> {
                    transaccion.cambiarEstado(request.getEstado());
                    transaccionRepository.save(transaccion);
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

    public static class ProcesarPagoRequest {
        private UUID contratoId;
        private double monto;
        private String metodoPago;

        public UUID getContratoId() {
            return contratoId;
        }

        public void setContratoId(UUID contratoId) {
            this.contratoId = contratoId;
        }

        public double getMonto() {
            return monto;
        }

        public void setMonto(double monto) {
            this.monto = monto;
        }

        public String getMetodoPago() {
            return metodoPago;
        }

        public void setMetodoPago(String metodoPago) {
            this.metodoPago = metodoPago;
        }
    }

    public static class TransaccionResponse {
        private UUID id;
        private UUID contratoId;
        private double monto;
        private String metodoPago;
        private String estado;

        public TransaccionResponse(UUID id, UUID contratoId, double monto, String metodoPago, String estado) {
            this.id = id;
            this.contratoId = contratoId;
            this.monto = monto;
            this.metodoPago = metodoPago;
            this.estado = estado;
        }

        public UUID getId() {
            return id;
        }

        public UUID getContratoId() {
            return contratoId;
        }

        public double getMonto() {
            return monto;
        }

        public String getMetodoPago() {
            return metodoPago;
        }

        public String getEstado() {
            return estado;
        }
    }
}
