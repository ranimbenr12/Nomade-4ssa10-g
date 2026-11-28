package tn.esprit.hotel.booking.dto;

import tn.esprit.hotel.booking.entity.Equipement;

import java.util.UUID;

public record EquipementResponse(UUID id, String libelle) {

    public static EquipementResponse from(Equipement e) {
        return new EquipementResponse(e.getId(), e.getLibelle());
    }
}