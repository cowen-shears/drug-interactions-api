package com.example.domain.ports;

import com.example.domain.model.InteractionNote;
import com.example.domain.model.DrugName;
import java.util.Optional;

public interface InteractionRepository {
    Optional<InteractionNote> findByDrugs(DrugName drugA, DrugName drugB);
    InteractionNote save(InteractionNote note);
}
