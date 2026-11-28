package tn.esprit.hotel.booking.dto;

import tn.esprit.hotel.booking.entity.Disponibilite;

import java.time.LocalDate;
import java.util.UUID;

public record DisponibiliteResponse(
        UUID id,
        UUID chambreId,
        UUID reservationId,
        LocalDate dateDebut,
        LocalDate dateFin,
        boolean disponible) {

    public static DisponibiliteResponse from(Disponibilite d) {
        return new DisponibiliteResponse(
                d.getId(),
                d.getChambre().getId(),
                d.getReservation() == null ? null : d.getReservation().getId(),
                d.getDateDebut(),
                d.getDateFin(),
                d.isDisponible());
    }
}