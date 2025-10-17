package com.example.app.controller;

import com.example.domain.model.SignalAnalysis;
import com.example.domain.service.SignalAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/signals")
@RequiredArgsConstructor
public class SignalController {
    private final SignalAnalysisService signalService;

    @GetMapping
    public ResponseEntity<Mono<SignalAnalysis>> getSignals(
            @RequestParam String drugA,
            @RequestParam String drugB,
            @RequestParam(defaultValue = "50") int limit) {
        return ResponseEntity.ok(signalService.analyzeSignals(drugA, drugB, limit));
    }
}
