package com.agricola.certifications.infrastructure.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, Object> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("service", "Certifications Service");
        response.put("status", "running");
        response.put("endpoints", Map.of(
            "certificaciones", "/api/certificaciones"
        ));
        return response;
    }
}
