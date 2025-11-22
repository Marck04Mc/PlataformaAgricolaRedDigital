package com.agricola.trading.infrastructure.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, Object> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("service", "Trading Service");
        response.put("status", "running");
        response.put("endpoints", Map.of(
            "contratos", "/api/contratos"
        ));
        return response;
    }
}
