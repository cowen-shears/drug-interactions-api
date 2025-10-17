package com.example.adapters.openfda;

import com.example.domain.model.DrugName;
import com.example.domain.model.SignalAnalysis;
import com.example.domain.model.SignalAnalysis.AdverseReaction;
import com.example.domain.ports.OpenFdaClient;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.stream.StreamSupport;

@Component
@RequiredArgsConstructor
public class OpenFdaClientAdapter implements OpenFdaClient {
    private final WebClient webClient;

    @Override
    public Mono<SignalAnalysis> getSignals(DrugName drugA, DrugName drugB, int limit) {
        String query = String.format("patient.drug.medicinalproduct:\"%s\"+AND+\"%s\"",
            drugA.getValue(), drugB.getValue());

        return webClient.get()
                .uri(builder -> builder
                    .path("/drug/event.json")
                    .queryParam("search", query)
                    .queryParam("count", "patient.reaction.reactionmeddrapt.exact")
                    .queryParam("limit", limit)
                    .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(this::parseResponse);
    }

    private SignalAnalysis parseResponse(JsonNode response) {
        JsonNode results = response.path("results");
        int totalCount = response.path("meta").path("results").path("total").asInt();

        List<AdverseReaction> reactions = StreamSupport.stream(results.spliterator(), false)
                .map(node -> new AdverseReaction(
                    node.path("term").asText(),
                    node.path("count").asInt()
                ))
                .sorted(Comparator.comparing(AdverseReaction::getCount).reversed())
                .toList();

        return new SignalAnalysis(totalCount, reactions);
    }
}
