package com.example.domain.exception;

import com.example.domain.model.DrugName;

public class InteractionNotFoundException extends RuntimeException {
    public InteractionNotFoundException(DrugName drugA, DrugName drugB) {
        super(String.format("No interaction found between %s and %s", drugA.getValue(), drugB.getValue()));
    }
}
