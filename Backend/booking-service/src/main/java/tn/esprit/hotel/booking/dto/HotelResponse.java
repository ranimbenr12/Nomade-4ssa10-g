package tn.esprit.hotel.booking.dto;

import tn.esprit.hotel.booking.entity.Hotel;

import java.util.UUID;

public record HotelResponse(
        UUID id,
        String nom,
        String adresse,
        String ville,
        int etoiles,
        UUID partnerId) {

    public static HotelResponse from(Hotel h) {
        return new HotelResponse(h.getId(), h.getNom(), h.getAdresse(),
                h.getVille(), h.getEtoiles(), h.getPartnerId());
    }
}