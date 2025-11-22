package com.agricola.payments.infrastructure.api;

import com.agricola.payments.application.ProcesarPagoUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/pagos")
public class PaymentController {

    private final ProcesarPagoUseCase procesarPagoUseCase;

    public PaymentController(ProcesarPagoUseCase procesarPagoUseCase) {
        this.procesarPagoUseCase = procesarPagoUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> procesar(@RequestBody ProcesarPagoRequest request) {
        procesarPagoUseCase.ejecutar(request.getContratoId(), request.getMonto(), request.getMetodoPago());
        return ResponseEntity.ok().build();
    }

    public static class ProcesarPagoRequest {
        private UUID contratoId;
        private double monto;
        private String metodoPago;

        public UUID getContratoId() { return contratoId; }
        public void setContratoId(UUID contratoId) { this.contratoId = contratoId; }
        public double getMonto() { return monto; }
        public void setMonto(double monto) { this.monto = monto; }
        public String getMetodoPago() { return metodoPago; }
        public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    }
}
