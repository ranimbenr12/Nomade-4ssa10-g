package tn.esprit.activity.mapper.activite;

import tn.esprit.activity.dto.activite.ActiviteRequest;
import tn.esprit.activity.dto.activite.ActiviteResponse;
import tn.esprit.activity.entity.activite.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class ActiviteMapper {

    private ActiviteMapper() {
    }

    /** Request → nouvelle entité (création) */
    public static Activite toEntity(ActiviteRequest request) {
        Activite activite = new Activite();
        updateEntity(activite, request);
        return activite;
    }

    /** Copie les données du Request dans une entité existante (création ou modification) */
    public static void updateEntity(Activite a, ActiviteRequest r) {
        a.setHotelId(r.hotelId());
        a.setNom(r.nom().trim());
        a.setDescription(r.description());
        a.setType(r.type());
        a.setEmplacement(r.emplacement());
        a.setLieu(r.lieu());
        a.setDureeMin(r.dureeMin());
        a.setPrix(r.prix());
        a.setPrixEnfant(r.prixEnfant());
        a.setPublicCible(r.publicCible() != null ? r.publicCible() : PublicCible.TOUS);
        a.setAgeMin(r.ageMin());
        a.setNiveau(r.niveau() != null ? r.niveau() : NiveauDifficulte.FACILE);
        a.setEquipementFourni(r.equipementFourni() != null && r.equipementFourni());
        a.setInclus(copie(r.inclus()));
        a.setaPrevoir(copie(r.aPrevoir()));
        a.setLangues(copie(r.langues()));
        a.setImages(copie(r.images()));
        a.setStatut(r.statut() != null ? r.statut() : StatutActivite.ACTIVE);
        a.setAttributs(r.attributs() != null ? new HashMap<>(r.attributs()) : new HashMap<>());
    }

    /** Entité → Response */
    public static ActiviteResponse toResponse(Activite a) {
        return new ActiviteResponse(
                a.getId(), a.getHotelId(), a.getNom(), a.getDescription(), a.getType(),
                a.getEmplacement(), a.getLieu(), a.getDureeMin(), a.getPrix(), a.getPrixEnfant(),
                a.getPublicCible(), a.getAgeMin(), a.getNiveau(), a.isEquipementFourni(),
                a.getInclus(), a.getaPrevoir(), a.getLangues(), a.getImages(),
                a.getNoteMoyenne(), a.getNombreAvis(), a.getStatut(), a.getAttributs(),
                a.getDateCreation(), a.getDateModification());
    }

    private static List<String> copie(List<String> liste) {
        return liste != null ? new ArrayList<>(liste) : new ArrayList<>();
    }
}