package com.example.tests.unit;

import com.example.domain.model.DrugName;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class DrugNameValidationTest {
    private Validator validator;

    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void shouldAcceptValidDrugName() {
        // Given
        DrugName drugName = new DrugName("Aspirin");

        // When/Then
        assertThat(validator.validate(drugName)).isEmpty();
    }

    @Test
    void shouldAcceptDrugNameWithSpacesAndHyphens() {
        // Given
        DrugName drugName = new DrugName("Calcium-Channel Blocker");

        // When/Then
        assertThat(validator.validate(drugName)).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "a",                    // too short
        "Ab",                   // too short
        "123",                  // numbers not allowed
        "Drug!",               // special characters not allowed
        "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" // 61 characters, too long
    })
    void shouldRejectInvalidDrugNames(String name) {
        // Given
        DrugName drugName = new DrugName(name);

        // When/Then
        assertThat(validator.validate(drugName)).isNotEmpty();
    }
}
