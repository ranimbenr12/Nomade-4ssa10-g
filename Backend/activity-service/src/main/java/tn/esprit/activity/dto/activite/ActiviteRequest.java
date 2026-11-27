package tn.esprit.activity.dto.activite;

import jakarta.validation.constraints.*;
import tn.esprit.activity.entity.activite.*;

import java.util.List;
import java.util.Map;

public record ActiviteRequest(

        @NotNull(message = "hotelId est obligatoire")
        @Positive(message = "hotelId doit être positif")
        Long hotelId,

        @NotBlank(message = "nom est obligatoire")
        @Size(max = 100, message = "nom : 100 caractères maximum")
        String nom,

        @Size(max = 1000, message = "description : 1000 caractères maximum")
        String description,

        @NotNull(message = "type est obligatoire")
        TypeActivite type,

        @NotNull(message = "emplacement est obligatoire (DANS_HOTEL ou HORS_HOTEL)")
        Emplacement emplacement,

        @NotBlank(message = "lieu est obligatoire")
        String lieu,

        @NotNull(message = "dureeMin est obligatoire")
        @Positive(message = "dureeMin doit être positif")
        Integer dureeMin,

        @NotNull(message = "prix est obligatoire")
        @PositiveOrZero(message = "prix doit être >= 0")
        Double prix,

        @PositiveOrZero(message = "prixEnfant doit être >= 0")
        Double prixEnfant,

        PublicCible publicCible,

        @Min(value = 0, message = "ageMin doit être >= 0")
        @Max(value = 99, message = "ageMin doit être <= 99")
        Integer ageMin,

        NiveauDifficulte niveau,

        Boolean equipementFourni,
        List<String> inclus,
        List<String> aPrevoir,
        List<String> langues,
        List<String> images,
        StatutActivite statut,
        Map<String, Object> attributs
) {
}