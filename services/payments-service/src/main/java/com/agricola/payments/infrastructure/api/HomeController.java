package com.agricola.payments.infrastructure.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, Object> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("service", "Payments Service");
        response.put("status", "running");
        response.put("endpoints", Map.of(
            "pagos", "/api/pagos"
        ));
        return response;
    }
}
