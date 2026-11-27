package tn.esprit.activity.dto.activite;

import tn.esprit.activity.entity.activite.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record ActiviteResponse(
        String id,
        Long hotelId,
        String nom,
        String description,
        TypeActivite type,
        Emplacement emplacement,
        String lieu,
        int dureeMin,
        double prix,
        Double prixEnfant,
        PublicCible publicCible,
        Integer ageMin,
        NiveauDifficulte niveau,
        boolean equipementFourni,
        List<String> inclus,
        List<String> aPrevoir,
        List<String> langues,
        List<String> images,
        double noteMoyenne,
        int nombreAvis,
        StatutActivite statut,
        Map<String, Object> attributs,
        LocalDateTime dateCreation,
        LocalDateTime dateModification
) {
}