package com.example.domain.ports;

import com.example.domain.model.DrugName;
import com.example.domain.model.SignalAnalysis;
import reactor.core.publisher.Mono;

public interface OpenFdaClient {
    Mono<SignalAnalysis> getSignals(DrugName drugA, DrugName drugB, int limit);
}
