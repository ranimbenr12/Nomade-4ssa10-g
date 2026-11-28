package tn.esprit.hotel.booking.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.hotel.booking.dto.ReservationRequest;
import tn.esprit.hotel.booking.dto.ReservationResponse;
import tn.esprit.hotel.booking.service.ReservationService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
@Tag(name = "Réservations")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse creer(@Valid @RequestBody ReservationRequest request) {
        return reservationService.creer(request);
    }

    @GetMapping
    public List<ReservationResponse> lister(@RequestParam(required = false) UUID clientId,
                                            @RequestParam(required = false) UUID chambreId) {
        return reservationService.lister(clientId, chambreId);
    }

    @GetMapping("/{id}")
    public ReservationResponse trouver(@PathVariable UUID id) {
        return reservationService.trouver(id);
    }

    @PutMapping("/{id}")
    public ReservationResponse modifier(@PathVariable UUID id, @Valid @RequestBody ReservationRequest request) {
        return reservationService.modifier(id, request);
    }

    @PatchMapping("/{id}/confirmer")
    public ReservationResponse confirmer(@PathVariable UUID id) {
        return reservationService.confirmer(id);
    }

    @PatchMapping("/{id}/annuler")
    public ReservationResponse annuler(@PathVariable UUID id) {
        return reservationService.annuler(id);
    }

    @PatchMapping("/{id}/demarrer")
    public ReservationResponse demarrer(@PathVariable UUID id) {
        return reservationService.demarrer(id);
    }

    @PatchMapping("/{id}/terminer")
    public ReservationResponse terminer(@PathVariable UUID id) {
        return reservationService.terminer(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void supprimer(@PathVariable UUID id) {
        reservationService.supprimer(id);
    }
}
