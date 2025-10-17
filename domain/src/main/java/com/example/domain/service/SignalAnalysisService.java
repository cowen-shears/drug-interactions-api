package com.example.domain.service;

import com.example.domain.model.DrugName;
import com.example.domain.model.SignalAnalysis;
import com.example.domain.ports.OpenFdaClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SignalAnalysisService {
    private final OpenFdaClient openFdaClient;

    public Mono<SignalAnalysis> analyzeSignals(String drugAName, String drugBName, int limit) {
        DrugName drugA = new DrugName(drugAName);
        DrugName drugB = new DrugName(drugBName);
        return openFdaClient.getSignals(drugA, drugB, limit);
    }
}
