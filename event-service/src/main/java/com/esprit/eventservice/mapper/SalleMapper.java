package com.esprit.eventservice.mapper;

import com.esprit.eventservice.dto.SalleRequest;
import com.esprit.eventservice.dto.SalleResponse;
import com.esprit.eventservice.entity.Salle;

public final class SalleMapper {

    private SalleMapper() {}

    public static Salle toEntity(SalleRequest request) {
        Salle salle = new Salle();
        salle.setNom(request.nom());
        salle.setCapacite(request.capacite());
        salle.setPrix(request.prix());
        salle.setHotelId(request.hotelId());
        return salle;
    }

    public static SalleResponse toResponse(Salle salle) {
        return new SalleResponse(
                salle.getId(),
                salle.getNom(),
                salle.getCapacite(),
                salle.getPrix(),
                salle.getHotelId()
        );
    }
}