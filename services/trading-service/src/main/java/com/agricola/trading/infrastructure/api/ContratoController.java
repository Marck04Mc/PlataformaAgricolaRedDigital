package com.agricola.trading.infrastructure.api;

import com.agricola.trading.application.CrearContratoUseCase;
import com.agricola.trading.domain.model.Contrato;
import com.agricola.trading.domain.repository.ContratoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/contratos")
public class ContratoController {

    private final CrearContratoUseCase crearContratoUseCase;
    private final ContratoRepository contratoRepository;

    public ContratoController(CrearContratoUseCase crearContratoUseCase, ContratoRepository contratoRepository) {
        this.crearContratoUseCase = crearContratoUseCase;
        this.contratoRepository = contratoRepository;
    }

    @GetMapping
    public ResponseEntity<List<ContratoResponse>> listar() {
        List<ContratoResponse> contratos = contratoRepository.findAll().stream()
                .map(c -> new ContratoResponse(c.getId(), c.getProductorId(), c.getCompradorId(), c.getTerminos()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(contratos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContratoResponse> obtener(@PathVariable UUID id) {
        return contratoRepository.findById(id)
                .map(c -> ResponseEntity
                        .ok(new ContratoResponse(c.getId(), c.getProductorId(), c.getCompradorId(), c.getTerminos())))
                .orElse(ResponseEntity.notFound().build());
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

        public UUID getProductorId() {
            return productorId;
        }

        public void setProductorId(UUID productorId) {
            this.productorId = productorId;
        }

        public UUID getCompradorId() {
            return compradorId;
        }

        public void setCompradorId(UUID compradorId) {
            this.compradorId = compradorId;
        }

        public String getTerminos() {
            return terminos;
        }

        public void setTerminos(String terminos) {
            this.terminos = terminos;
        }
    }

    public static class ContratoResponse {
        private UUID id;
        private UUID productorId;
        private UUID compradorId;
        private String terminos;

        public ContratoResponse(UUID id, UUID productorId, UUID compradorId, String terminos) {
            this.id = id;
            this.productorId = productorId;
            this.compradorId = compradorId;
            this.terminos = terminos;
        }

        public UUID getId() {
            return id;
        }

        public UUID getProductorId() {
            return productorId;
        }

        public UUID getCompradorId() {
            return compradorId;
        }

        public String getTerminos() {
            return terminos;
        }
    }
}
