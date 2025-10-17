package com.example.tests.integration;

import com.example.app.DrugInteractionsApplication;
import com.example.domain.model.SignalAnalysis;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
    classes = DrugInteractionsApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class SignalControllerIntegrationTest {
    @RegisterExtension
    static WireMockExtension openFda = WireMockExtension.newInstance()
        .options(wireMockConfig().dynamicPort())
        .build();

    @DynamicPropertySource
    static void configureOpenFdaUrl(DynamicPropertyRegistry registry) {
        registry.add("openfda.baseUrl", () -> openFda.baseUrl());
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldReturnSignalAnalysis() {
        // Given
        openFda.stubFor(get(urlPathMatching("/drug/event.json"))
            .withQueryParam("search", containing("Warfarin"))
            .withQueryParam("search", containing("Aspirin"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("""
                    {
                        "meta": {"results": {"total": 156}},
                        "results": [
                            {"term": "Gastrointestinal hemorrhage", "count": 23},
                            {"term": "International normalised ratio increased", "count": 18}
                        ]
                    }
                    """)));

        // When
        ResponseEntity<SignalAnalysis> response = restTemplate.getForEntity(
            "/signals?drugA=Warfarin&drugB=Aspirin",
            SignalAnalysis.class
        );

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getCount()).isEqualTo(156);
        assertThat(response.getBody().getTopReactions()).hasSize(2);
        assertThat(response.getBody().getTopReactions().get(0).getReaction())
            .isEqualTo("Gastrointestinal hemorrhage");
    }
}
