package tn.esprit.hotel.booking.dto;

import jakarta.validation.constraints.NotBlank;

public record EquipementRequest(@NotBlank String libelle) {
}