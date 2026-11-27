package com.esprit.eventservice.controller;

import com.esprit.eventservice.dto.DevisResponse;
import com.esprit.eventservice.service.DevisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/devis")
@RequiredArgsConstructor
public class DevisController {

    private final DevisService devisService;

    @GetMapping("/{id}")
    public DevisResponse trouver(@PathVariable Long id) {
        return devisService.trouver(id);
    }

    @PatchMapping("/{id}/valider")
    public DevisResponse valider(@PathVariable Long id) {
        return devisService.valider(id);
    }
}