package com.example.domain.model;

import lombok.Value;
import java.util.List;

@Value
public class SignalAnalysis {
    int count;
    List<AdverseReaction> topReactions;

    @Value
    public static class AdverseReaction {
        String reaction;
        int count;
    }
}
