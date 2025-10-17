package com.example.adapters.memory;

import com.example.domain.model.DrugName;
import com.example.domain.model.InteractionNote;
import com.example.domain.ports.InteractionRepository;
import org.springframework.stereotype.Repository;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryInteractionRepository implements InteractionRepository {
    private final Map<String, InteractionNote> store = new ConcurrentHashMap<>();

    private String buildKey(DrugName drugA, DrugName drugB) {
        // Normalize key order to ensure consistent lookup regardless of parameter order
        return drugA.getValue().compareTo(drugB.getValue()) <= 0
                ? drugA.getValue() + ":" + drugB.getValue()
                : drugB.getValue() + ":" + drugA.getValue();
    }

    @Override
    public Optional<InteractionNote> findByDrugs(DrugName drugA, DrugName drugB) {
        return Optional.ofNullable(store.get(buildKey(drugA, drugB)));
    }

    @Override
    public InteractionNote save(InteractionNote note) {
        store.put(buildKey(new DrugName(note.getDrugA().getValue()), new DrugName(note.getDrugB().getValue())), note);
        return note;
    }
}
