package com.agricola.certifications.infrastructure.api;

import com.agricola.certifications.application.RegistrarCertificacionUseCase;
import com.agricola.certifications.domain.model.Certificacion;
import com.agricola.certifications.domain.repository.CertificacionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/certificaciones")
public class CertificationController {

    private final RegistrarCertificacionUseCase registrarCertificacionUseCase;
    private final CertificacionRepository certificacionRepository;

    public CertificationController(RegistrarCertificacionUseCase registrarCertificacionUseCase,
            CertificacionRepository certificacionRepository) {
        this.registrarCertificacionUseCase = registrarCertificacionUseCase;
        this.certificacionRepository = certificacionRepository;
    }

    @GetMapping
    public ResponseEntity<List<CertificacionResponse>> listar() {
        List<CertificacionResponse> certificaciones = certificacionRepository.findAll().stream()
                .map(c -> new CertificacionResponse(c.getId(), c.getProductorId(), c.getTipo(), c.getUrlDocumento()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(certificaciones);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificacionResponse> obtener(@PathVariable UUID id) {
        return certificacionRepository.findById(id)
                .map(c -> ResponseEntity
                        .ok(new CertificacionResponse(c.getId(), c.getProductorId(), c.getTipo(), c.getUrlDocumento())))
                .orElse(ResponseEntity.notFound().build());
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

        public UUID getProductorId() {
            return productorId;
        }

        public void setProductorId(UUID productorId) {
            this.productorId = productorId;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public String getUrlDocumento() {
            return urlDocumento;
        }

        public void setUrlDocumento(String urlDocumento) {
            this.urlDocumento = urlDocumento;
        }
    }

    public static class CertificacionResponse {
        private UUID id;
        private UUID productorId;
        private String tipo;
        private String urlDocumento;

        public CertificacionResponse(UUID id, UUID productorId, String tipo, String urlDocumento) {
            this.id = id;
            this.productorId = productorId;
            this.tipo = tipo;
            this.urlDocumento = urlDocumento;
        }

        public UUID getId() {
            return id;
        }

        public UUID getProductorId() {
            return productorId;
        }

        public String getTipo() {
            return tipo;
        }

        public String getUrlDocumento() {
            return urlDocumento;
        }
    }
}
