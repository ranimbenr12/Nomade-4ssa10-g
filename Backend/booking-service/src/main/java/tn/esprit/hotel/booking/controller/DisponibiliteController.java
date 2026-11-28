package tn.esprit.hotel.booking.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.hotel.booking.dto.DisponibiliteRequest;
import tn.esprit.hotel.booking.dto.DisponibiliteResponse;
import tn.esprit.hotel.booking.service.DisponibiliteService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/disponibilites")
@RequiredArgsConstructor
@Tag(name = "Disponibilités")
public class DisponibiliteController {

    private final DisponibiliteService disponibiliteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DisponibiliteResponse creer(@Valid @RequestBody DisponibiliteRequest request) {
        return disponibiliteService.creer(request);
    }

    @GetMapping
    public List<DisponibiliteResponse> lister(@RequestParam(required = false) UUID chambreId) {
        return disponibiliteService.lister(chambreId);
    }

    // vrai si la chambre est libre sur toute la période demandée
    @GetMapping("/verifier")
    public boolean verifier(@RequestParam UUID chambreId,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return disponibiliteService.verifier(chambreId, debut, fin);
    }

    @GetMapping("/{id}")
    public DisponibiliteResponse trouver(@PathVariable UUID id) {
        return disponibiliteService.trouver(id);
    }

    @PutMapping("/{id}")
    public DisponibiliteResponse modifier(@PathVariable UUID id, @Valid @RequestBody DisponibiliteRequest request) {
        return disponibiliteService.modifier(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void supprimer(@PathVariable UUID id) {
        disponibiliteService.supprimer(id);
    }
}