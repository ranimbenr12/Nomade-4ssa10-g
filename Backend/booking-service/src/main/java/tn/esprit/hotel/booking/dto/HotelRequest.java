package tn.esprit.hotel.booking.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record HotelRequest(
        @NotBlank String nom,
        @NotBlank String adresse,
        @NotBlank String ville,
        @Min(1) @Max(5) int etoiles,
        UUID partnerId) {
}