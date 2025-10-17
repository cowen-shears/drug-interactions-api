package com.example.tests.integration;

import com.example.app.DrugInteractionsApplication;
import com.example.domain.model.InteractionNote;
import com.example.tests.fixtures.TestFixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
    classes = DrugInteractionsApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class InteractionControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldUpsertAndRetrieveInteraction() {
        // Given
        InteractionNote note = TestFixtures.createSampleInteractionNote();

        // When
        ResponseEntity<InteractionNote> createResponse = restTemplate.postForEntity(
            "/interactions",
            note,
            InteractionNote.class
        );

        // Then
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(createResponse.getBody()).isNotNull();
        assertThat(createResponse.getBody().getNote()).isEqualTo(note.getNote());

        // When retrieving
        ResponseEntity<InteractionNote> getResponse = restTemplate.getForEntity(
            String.format("/interactions?drugA=%s&drugB=%s",
                note.getDrugA().getValue(),
                note.getDrugB().getValue()),
            InteractionNote.class
        );

        // Then
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody()).isNotNull();
        assertThat(getResponse.getBody()).isEqualTo(note);
    }
}
