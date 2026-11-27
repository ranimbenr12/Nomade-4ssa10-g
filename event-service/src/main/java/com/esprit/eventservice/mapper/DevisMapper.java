package com.esprit.eventservice.mapper;

import com.esprit.eventservice.dto.DevisResponse;
import com.esprit.eventservice.entity.Devis;

public final class DevisMapper {

    private DevisMapper() {}

    public static DevisResponse toResponse(Devis devis) {
        return new DevisResponse(
                devis.getId(),
                devis.getMontantTotal(),
                devis.getStatut(),
                devis.getDateValidite(),
                devis.getEvenement().getId()
        );
    }
}