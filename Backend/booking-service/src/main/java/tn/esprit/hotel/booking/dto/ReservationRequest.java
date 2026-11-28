package tn.esprit.hotel.booking.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record ReservationRequest(
        @NotNull UUID clientId,
        @NotNull UUID chambreId,
        @NotNull LocalDate dateDebut,
        @NotNull LocalDate dateFin,
        @Min(1) int nbPersonnes,
        UUID paymentId) {
}