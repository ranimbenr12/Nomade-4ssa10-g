package tn.esprit.hotel.booking.dto;

import tn.esprit.hotel.booking.entity.Chambre;
import tn.esprit.hotel.booking.enums.TypeChambre;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ChambreResponse(
        UUID id,
        UUID hotelId,
        String numero,
        TypeChambre type,
        int capacite,
        BigDecimal prixParNuit,
        boolean active,
        List<EquipementResponse> equipements) {

    public static ChambreResponse from(Chambre c) {
        return new ChambreResponse(
                c.getId(),
                c.getHotel().getId(),
                c.getNumero(),
                c.getType(),
                c.getCapacite(),
                c.getPrixParNuit(),
                c.isActive(),
                c.getEquipements().stream().map(EquipementResponse::from).toList());
    }
}