package tn.esprit.hotel.booking.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import tn.esprit.hotel.booking.enums.TypeChambre;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

public record ChambreRequest(
        @NotNull UUID hotelId,
        @NotBlank String numero,
        @NotNull TypeChambre type,
        @Min(1) int capacite,
        @NotNull @DecimalMin(value = "0.0", inclusive = false) BigDecimal prixParNuit,
        Boolean active,
        Set<UUID> equipementIds) {
}