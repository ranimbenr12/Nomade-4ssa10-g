package tn.esprit.hotel.booking.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.hotel.booking.dto.ChambreRequest;
import tn.esprit.hotel.booking.dto.ChambreResponse;
import tn.esprit.hotel.booking.service.ChambreService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/chambres")
@RequiredArgsConstructor
@Tag(name = "Chambres")
public class ChambreController {

    private final ChambreService chambreService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ChambreResponse creer(@Valid @RequestBody ChambreRequest request) {
        return chambreService.creer(request);
    }

    @GetMapping
    public List<ChambreResponse> lister(@RequestParam(required = false) UUID hotelId) {
        return chambreService.lister(hotelId);
    }

    @GetMapping("/{id}")
    public ChambreResponse trouver(@PathVariable UUID id) {
        return chambreService.trouver(id);
    }

    @PutMapping("/{id}")
    public ChambreResponse modifier(@PathVariable UUID id, @Valid @RequestBody ChambreRequest request) {
        return chambreService.modifier(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void supprimer(@PathVariable UUID id) {
        chambreService.supprimer(id);
    }
}