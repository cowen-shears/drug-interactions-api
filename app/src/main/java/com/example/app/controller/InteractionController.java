package com.example.app.controller;

import com.example.domain.model.InteractionNote;
import com.example.domain.service.InteractionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interactions")
@RequiredArgsConstructor
public class InteractionController {
    private final InteractionService interactionService;

    @PostMapping
    public ResponseEntity<InteractionNote> upsertInteraction(
            @Valid @RequestBody InteractionNote note) {
        return ResponseEntity.ok(interactionService.upsertInteraction(note));
    }

    @GetMapping
    public ResponseEntity<InteractionNote> getInteraction(
            @RequestParam String drugA,
            @RequestParam String drugB) {
        return ResponseEntity.ok(interactionService.getInteraction(drugA, drugB));
    }
}
