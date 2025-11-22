package com.agricola.trading.infrastructure.api;

import com.agricola.trading.application.CrearContratoUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/contratos")
public class ContratoController {

    private final CrearContratoUseCase crearContratoUseCase;

    public ContratoController(CrearContratoUseCase crearContratoUseCase) {
        this.crearContratoUseCase = crearContratoUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> crear(@RequestBody CrearContratoRequest request) {
        crearContratoUseCase.ejecutar(request.getProductorId(), request.getCompradorId(), request.getTerminos());
        return ResponseEntity.ok().build();
    }

    public static class CrearContratoRequest {
        private UUID productorId;
        private UUID compradorId;
        private String terminos;

        public UUID getProductorId() { return productorId; }
        public void setProductorId(UUID productorId) { this.productorId = productorId; }
        public UUID getCompradorId() { return compradorId; }
        public void setCompradorId(UUID compradorId) { this.compradorId = compradorId; }
        public String getTerminos() { return terminos; }
        public void setTerminos(String terminos) { this.terminos = terminos; }
    }
}
