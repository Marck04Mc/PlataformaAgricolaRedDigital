package com.agricola.certifications.infrastructure.api;

import com.agricola.certifications.application.RegistrarCertificacionUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/certificaciones")
public class CertificationController {

    private final RegistrarCertificacionUseCase registrarCertificacionUseCase;

    public CertificationController(RegistrarCertificacionUseCase registrarCertificacionUseCase) {
        this.registrarCertificacionUseCase = registrarCertificacionUseCase;
    }

    @PostMapping
    public ResponseEntity<Void> registrar(@RequestBody RegistrarCertificacionRequest request) {
        registrarCertificacionUseCase.ejecutar(request.getProductorId(), request.getTipo(), request.getUrlDocumento());
        return ResponseEntity.ok().build();
    }

    public static class RegistrarCertificacionRequest {
        private UUID productorId;
        private String tipo;
        private String urlDocumento;

        public UUID getProductorId() { return productorId; }
        public void setProductorId(UUID productorId) { this.productorId = productorId; }
        public String getTipo() { return tipo; }
        public void setTipo(String tipo) { this.tipo = tipo; }
        public String getUrlDocumento() { return urlDocumento; }
        public void setUrlDocumento(String urlDocumento) { this.urlDocumento = urlDocumento; }
    }
}
