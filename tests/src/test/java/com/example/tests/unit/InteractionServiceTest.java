package com.example.tests.unit;

import com.example.domain.exceptions.InteractionNotFoundException;
import com.example.domain.model.InteractionNote;
import com.example.domain.ports.InteractionRepository;
import com.example.domain.service.InteractionService;
import com.example.tests.fixtures.TestFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InteractionServiceTest {
    @Mock
    private InteractionRepository repository;

    private InteractionService service;

    @BeforeEach
    void setUp() {
        service = new InteractionService(repository);
    }

    @Test
    void shouldSaveInteractionNote() {
        // Given
        InteractionNote note = TestFixtures.createSampleInteractionNote();
        when(repository.save(any())).thenReturn(note);

        // When
        InteractionNote result = service.upsertInteraction(note);

        // Then
        assertThat(result).isEqualTo(note);
    }

    @Test
    void shouldRetrieveExistingInteraction() {
        // Given
        InteractionNote note = TestFixtures.createSampleInteractionNote();
        when(repository.findByDrugs(note.getDrugA(), note.getDrugB()))
            .thenReturn(Optional.of(note));

        // When
        InteractionNote result = service.getInteraction(
            note.getDrugA().getValue(),
            note.getDrugB().getValue()
        );

        // Then
        assertThat(result).isEqualTo(note);
    }

    @Test
    void shouldThrowExceptionWhenInteractionNotFound() {
        // Given
        when(repository.findByDrugs(any(), any())).thenReturn(Optional.empty());

        // When/Then
        assertThatThrownBy(() -> service.getInteraction("Warfarin", "Aspirin"))
            .isInstanceOf(InteractionNotFoundException.class)
            .hasMessage("No interaction found between Warfarin and Aspirin");
    }
}
