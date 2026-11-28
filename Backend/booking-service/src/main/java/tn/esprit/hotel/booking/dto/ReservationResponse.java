package tn.esprit.hotel.booking.dto;

import tn.esprit.hotel.booking.entity.Reservation;
import tn.esprit.hotel.booking.enums.StatutReservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record ReservationResponse(
        UUID id,
        UUID clientId,
        UUID chambreId,
        UUID hotelId,
        UUID paymentId,
        LocalDate dateDebut,
        LocalDate dateFin,
        int nbPersonnes,
        BigDecimal montantTotal,
        StatutReservation statut,
        LocalDateTime dateCreation) {

    public static ReservationResponse from(Reservation r) {
        return new ReservationResponse(
                r.getId(), r.getClientId(), r.getChambre().getId(), r.getHotelId(),
                r.getPaymentId(), r.getDateDebut(), r.getDateFin(), r.getNbPersonnes(),
                r.getMontantTotal(), r.getStatut(), r.getDateCreation());
    }
}