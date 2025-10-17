package com.example.domain.service;

import com.example.domain.exceptions.InteractionNotFoundException;
import com.example.domain.model.DrugName;
import com.example.domain.model.InteractionNote;
import com.example.domain.ports.InteractionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import jakarta.validation.Valid;

@Service
@RequiredArgsConstructor
public class InteractionService {
    private final InteractionRepository repository;

    public InteractionNote upsertInteraction(@Valid InteractionNote note) {
        return repository.save(note);
    }

    public InteractionNote getInteraction(String drugAName, String drugBName) {
        DrugName drugA = new DrugName(drugAName);
        DrugName drugB = new DrugName(drugBName);
        return repository.findByDrugs(drugA, drugB)
                .orElseThrow(() -> new InteractionNotFoundException(String.format("No interaction found between %s and %s", drugAName, drugBName)));
    }
}
