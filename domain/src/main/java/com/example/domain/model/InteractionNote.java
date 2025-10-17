package com.example.domain.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class InteractionNote {
    @Valid
    DrugName drugA;

    @Valid
    DrugName drugB;

    @NotBlank
    @Size(min = 1, max = 1000)
    String note;

    @JsonCreator
    public InteractionNote(
        @JsonProperty("drugA") DrugName drugA,
        @JsonProperty("drugB") DrugName drugB,
        @JsonProperty("note") String note) {
        this.drugA = drugA;
        this.drugB = drugB;
        this.note = note;
    }
}
