package com.agricola.inventory.infrastructure.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, Object> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("service", "Inventory Service");
        response.put("status", "running");
        response.put("endpoints", Map.of(
            "productos", "/api/productos"
        ));
        return response;
    }
}
