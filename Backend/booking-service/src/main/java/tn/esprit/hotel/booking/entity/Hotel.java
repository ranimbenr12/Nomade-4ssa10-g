package tn.esprit.hotel.booking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nom;
    private String adresse;
    private String ville;
    private int etoiles;

    // lien par id vers le service Partner (pas de clé étrangère)
    private UUID partnerId;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chambre> chambres = new ArrayList<>();

    public void ajouterChambre(Chambre chambre) {
        chambres.add(chambre);
        chambre.setHotel(this);
    }
}