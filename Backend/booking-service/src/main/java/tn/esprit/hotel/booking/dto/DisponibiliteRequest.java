package tn.esprit.hotel.booking.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record DisponibiliteRequest(
        @NotNull UUID chambreId,
        @NotNull LocalDate dateDebut,
        @NotNull LocalDate dateFin,
        @NotNull Boolean disponible) {
}