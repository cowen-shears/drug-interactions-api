package com.example.tests.unit;

import com.example.domain.model.DrugName;
import com.example.domain.model.SignalAnalysis;
import com.example.domain.ports.OpenFdaClient;
import com.example.domain.service.SignalAnalysisService;
import com.example.tests.fixtures.TestFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SignalAnalysisServiceTest {
    @Mock
    private OpenFdaClient openFdaClient;

    private SignalAnalysisService service;

    @BeforeEach
    void setUp() {
        service = new SignalAnalysisService(openFdaClient);
    }

    @Test
    void shouldAnalyzeSignals() {
        // Given
        SignalAnalysis expected = TestFixtures.createSampleSignalAnalysis();
        when(openFdaClient.getSignals(any(DrugName.class), any(DrugName.class), eq(50)))
            .thenReturn(Mono.just(expected));

        // When
        Mono<SignalAnalysis> result = service.analyzeSignals("Warfarin", "Aspirin", 50);

        // Then
        StepVerifier.create(result)
            .expectNext(expected)
            .verifyComplete();
    }

    @Test
    void shouldPropagateErrors() {
        // Given
        when(openFdaClient.getSignals(any(DrugName.class), any(DrugName.class), eq(50)))
            .thenReturn(Mono.error(new RuntimeException("OpenFDA Error")));

        // When
        Mono<SignalAnalysis> result = service.analyzeSignals("Warfarin", "Aspirin", 50);

        // Then
        StepVerifier.create(result)
            .expectErrorMatches(throwable ->
                throwable instanceof RuntimeException &&
                throwable.getMessage().equals("OpenFDA Error"))
            .verify();
    }
}
