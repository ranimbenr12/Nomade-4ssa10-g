package tn.esprit.hotel.booking.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.hotel.booking.dto.EquipementRequest;
import tn.esprit.hotel.booking.dto.EquipementResponse;
import tn.esprit.hotel.booking.service.EquipementService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/equipements")
@RequiredArgsConstructor
@Tag(name = "Équipements")
public class EquipementController {

    private final EquipementService equipementService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EquipementResponse creer(@Valid @RequestBody EquipementRequest request) {
        return equipementService.creer(request);
    }

    @GetMapping
    public List<EquipementResponse> lister() {
        return equipementService.lister();
    }

    @GetMapping("/{id}")
    public EquipementResponse trouver(@PathVariable UUID id) {
        return equipementService.trouver(id);
    }

    @PutMapping("/{id}")
    public EquipementResponse modifier(@PathVariable UUID id, @Valid @RequestBody EquipementRequest request) {
        return equipementService.modifier(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void supprimer(@PathVariable UUID id) {
        equipementService.supprimer(id);
    }
}