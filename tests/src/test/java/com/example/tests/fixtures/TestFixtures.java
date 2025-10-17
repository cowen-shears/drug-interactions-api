package com.example.tests.fixtures;

import com.example.domain.model.DrugName;
import com.example.domain.model.InteractionNote;
import com.example.domain.model.SignalAnalysis;
import java.util.List;

public class TestFixtures {
    public static final DrugName WARFARIN = new DrugName("Warfarin");
    public static final DrugName ASPIRIN = new DrugName("Aspirin");

    public static InteractionNote createSampleInteractionNote() {
        return new InteractionNote(
            WARFARIN,
            ASPIRIN,
            "Increased risk of bleeding. Monitor closely."
        );
    }

    public static SignalAnalysis createSampleSignalAnalysis() {
        return new SignalAnalysis(156, List.of(
            new SignalAnalysis.AdverseReaction("Gastrointestinal hemorrhage", 23),
            new SignalAnalysis.AdverseReaction("International normalised ratio increased", 18)
        ));
    }
}
