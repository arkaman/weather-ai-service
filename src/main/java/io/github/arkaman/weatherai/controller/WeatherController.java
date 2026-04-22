package io.github.arkaman.weatherai.controller;

import io.github.arkaman.weatherai.domain.dto.ForecastResponse;
import io.github.arkaman.weatherai.service.GeminiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/weather")
@CrossOrigin
public class WeatherController {

    private final GeminiService service;

    public WeatherController(GeminiService service) {
        this.service = service;
    }

    @PostMapping("/advice")
    public ResponseEntity<Map<String, String>> getAdvice(@RequestBody ForecastResponse response) {

        if (response == null || response.list == null || response.list.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Invalid forecast data"));
        }

        String advice = service.generateAdvice(response.list);

        return ResponseEntity.ok(Map.of("advice", advice));
    }
}