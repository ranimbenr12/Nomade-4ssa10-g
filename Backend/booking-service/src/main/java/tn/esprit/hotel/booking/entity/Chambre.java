package tn.esprit.hotel.booking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.hotel.booking.enums.TypeChambre;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Chambre {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String numero;

    @Enumerated(EnumType.STRING)
    private TypeChambre type;

    private int capacite;

    @Column(precision = 10, scale = 2)
    private BigDecimal prixParNuit;

    private boolean active = true;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    @ManyToMany
    @JoinTable(
            name = "chambre_equipement",
            joinColumns = @JoinColumn(name = "chambre_id"),
            inverseJoinColumns = @JoinColumn(name = "equipement_id"))
    private Set<Equipement> equipements = new HashSet<>();

    @OneToMany(mappedBy = "chambre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Disponibilite> disponibilites = new ArrayList<>();

    public boolean verifierDisponibilite(LocalDate debut, LocalDate fin) {
        if (!active) {
            return false;
        }
        return disponibilites.stream().noneMatch(d ->
                !d.isDisponible()
                        && d.getDateDebut().isBefore(fin)
                        && d.getDateFin().isAfter(debut));
    }
}