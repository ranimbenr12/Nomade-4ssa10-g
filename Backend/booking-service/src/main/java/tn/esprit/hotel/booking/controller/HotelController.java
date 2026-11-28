package tn.esprit.hotel.booking.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.hotel.booking.dto.HotelRequest;
import tn.esprit.hotel.booking.dto.HotelResponse;
import tn.esprit.hotel.booking.service.HotelService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
@Tag(name = "Hôtels")
public class HotelController {

    private final HotelService hotelService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HotelResponse creer(@Valid @RequestBody HotelRequest request) {
        return hotelService.creer(request);
    }

    @GetMapping
    public List<HotelResponse> lister(@RequestParam(required = false) String ville) {
        return hotelService.lister(ville);
    }

    @GetMapping("/{id}")
    public HotelResponse trouver(@PathVariable UUID id) {
        return hotelService.trouver(id);
    }

    @PutMapping("/{id}")
    public HotelResponse modifier(@PathVariable UUID id, @Valid @RequestBody HotelRequest request) {
        return hotelService.modifier(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void supprimer(@PathVariable UUID id) {
        hotelService.supprimer(id);
    }
}