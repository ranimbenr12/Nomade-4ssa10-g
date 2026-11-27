package com.esprit.eventservice.controller;

import com.esprit.eventservice.dto.EvenementRequest;
import com.esprit.eventservice.dto.EvenementResponse;
import com.esprit.eventservice.dto.OptionRequest;
import com.esprit.eventservice.enums.StatutEvenement;
import com.esprit.eventservice.service.EvenementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evenements")
@RequiredArgsConstructor
public class EvenementController {

    private final EvenementService evenementService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EvenementResponse creer(@Valid @RequestBody EvenementRequest request) {
        return evenementService.creer(request);
    }

    @GetMapping
    public List<EvenementResponse> lister() {
        return evenementService.lister();
    }

    @GetMapping("/{id}")
    public EvenementResponse trouver(@PathVariable Long id) {
        return evenementService.trouver(id);
    }

    @PostMapping("/{id}/options")
    public EvenementResponse ajouterOption(@PathVariable Long id,
                                           @Valid @RequestBody OptionRequest request) {
        return evenementService.ajouterOption(id, request);
    }

    @PatchMapping("/{id}/statut")
    public EvenementResponse changerStatut(@PathVariable Long id,
                                           @RequestParam StatutEvenement statut) {
        return evenementService.changerStatut(id, statut);
    }

    @PostMapping("/{id}/devis")
    @ResponseStatus(HttpStatus.CREATED)
    public EvenementResponse genererDevis(@PathVariable Long id) {
        return evenementService.genererDevis(id);
    }
}