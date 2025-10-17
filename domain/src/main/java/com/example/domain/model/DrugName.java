package com.example.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class DrugName {
    @Pattern(regexp = "^[A-Za-z][A-Za-z\\- ]{1,58}[A-Za-z]$", message = "Must contain only letters, spaces, and hyphens")
    @Size(min = 3, max = 60, message = "Must be between 3 and 60 characters")
    String value;

    @JsonCreator
    public DrugName(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
