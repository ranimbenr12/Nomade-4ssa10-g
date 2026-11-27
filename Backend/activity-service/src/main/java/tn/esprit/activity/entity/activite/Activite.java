package tn.esprit.activity.entity.activite;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document(collection = "activites")
public class Activite {

    @Id
    private String id;

    // Identification
    @Indexed
    private Long hotelId;
    private String nom;
    private String description;
    private TypeActivite type;

    // Lieu et durée
    private Emplacement emplacement;
    private String lieu;
    private int dureeMin;

    // Prix
    private double prix;
    private Double prixEnfant;

    // Public
    private PublicCible publicCible = PublicCible.TOUS;
    private Integer ageMin;
    private NiveauDifficulte niveau = NiveauDifficulte.FACILE;

    // Détails pratiques
    private boolean equipementFourni;
    private List<String> inclus = new ArrayList<>();
    private List<String> aPrevoir = new ArrayList<>();
    private List<String> langues = new ArrayList<>();
    private List<String> images = new ArrayList<>();

    // Avis
    private double noteMoyenne = 0;
    private int nombreAvis = 0;

    // Statut
    private StatutActivite statut = StatutActivite.ACTIVE;

    // Attributs propres à chaque type
    private Map<String, Object> attributs = new HashMap<>();

    // Dates
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;

    public Activite() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TypeActivite getType() {
        return type;
    }

    public void setType(TypeActivite type) {
        this.type = type;
    }

    public Emplacement getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(Emplacement emplacement) {
        this.emplacement = emplacement;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public int getDureeMin() {
        return dureeMin;
    }

    public void setDureeMin(int dureeMin) {
        this.dureeMin = dureeMin;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Double getPrixEnfant() {
        return prixEnfant;
    }

    public void setPrixEnfant(Double prixEnfant) {
        this.prixEnfant = prixEnfant;
    }

    public PublicCible getPublicCible() {
        return publicCible;
    }

    public void setPublicCible(PublicCible publicCible) {
        this.publicCible = publicCible;
    }

    public Integer getAgeMin() {
        return ageMin;
    }

    public void setAgeMin(Integer ageMin) {
        this.ageMin = ageMin;
    }

    public NiveauDifficulte getNiveau() {
        return niveau;
    }

    public void setNiveau(NiveauDifficulte niveau) {
        this.niveau = niveau;
    }

    public boolean isEquipementFourni() {
        return equipementFourni;
    }

    public void setEquipementFourni(boolean equipementFourni) {
        this.equipementFourni = equipementFourni;
    }

    public List<String> getInclus() {
        return inclus;
    }

    public void setInclus(List<String> inclus) {
        this.inclus = inclus;
    }

    public List<String> getaPrevoir() {
        return aPrevoir;
    }

    public void setaPrevoir(List<String> aPrevoir) {
        this.aPrevoir = aPrevoir;
    }

    public List<String> getLangues() {
        return langues;
    }

    public void setLangues(List<String> langues) {
        this.langues = langues;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public double getNoteMoyenne() {
        return noteMoyenne;
    }

    public void setNoteMoyenne(double noteMoyenne) {
        this.noteMoyenne = noteMoyenne;
    }

    public int getNombreAvis() {
        return nombreAvis;
    }

    public void setNombreAvis(int nombreAvis) {
        this.nombreAvis = nombreAvis;
    }

    public StatutActivite getStatut() {
        return statut;
    }

    public void setStatut(StatutActivite statut) {
        this.statut = statut;
    }

    public Map<String, Object> getAttributs() {
        return attributs;
    }

    public void setAttributs(Map<String, Object> attributs) {
        this.attributs = attributs;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateTime getDateModification() {
        return dateModification;
    }

    public void setDateModification(LocalDateTime dateModification) {
        this.dateModification = dateModification;
    }
}