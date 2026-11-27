package com.esprit.eventservice.mapper;

import com.esprit.eventservice.dto.EvenementRequest;
import com.esprit.eventservice.dto.EvenementResponse;
import com.esprit.eventservice.entity.Evenement;

public final class EvenementMapper {

    private EvenementMapper() {}

    // La salle est rattachée dans le service, à partir de salleId
    public static Evenement toEntity(EvenementRequest request) {
        Evenement evenement = new Evenement();
        evenement.setType(request.type());
        evenement.setDate(request.date());
        evenement.setNbInvites(request.nbInvites());
        evenement.setClientId(request.clientId());
        return evenement;
    }

    public static EvenementResponse toResponse(Evenement evenement) {
        return new EvenementResponse(
                evenement.getId(),
                evenement.getType(),
                evenement.getDate(),
                evenement.getNbInvites(),
                evenement.getStatut(),
                evenement.getClientId(),
                evenement.getSalle() != null ? evenement.getSalle().getId() : null,
                evenement.getOptions().stream()
                        .map(OptionMapper::toResponse)
                        .toList(),
                evenement.calculerTotal()
        );
    }
}