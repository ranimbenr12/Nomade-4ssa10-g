package com.esprit.eventservice.entity;

import com.esprit.eventservice.enums.StatutEvenement;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "evenements")
@Getter
@Setter
@NoArgsConstructor
public class Evenement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private LocalDate date;

    private int nbInvites;

    @Enumerated(EnumType.STRING)
    private StatutEvenement statut = StatutEvenement.BROUILLON;

    // Référence vers le User Service
    private Long clientId;

    // Nullable : un événement peut être hors salle
    @ManyToOne
    @JoinColumn(name = "salle_id")
    private Salle salle;

    @OneToMany(mappedBy = "evenement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options = new ArrayList<>();

    @OneToOne(mappedBy = "evenement", cascade = CascadeType.ALL)
    private Devis devis;

    public void ajouterOption(Option option) {
        option.setEvenement(this);
        options.add(option);
    }

    public BigDecimal calculerTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (Option option : options) {
            total = total.add(option.calculerPrix());
        }
        return total;
    }

    public void changerStatut(StatutEvenement nouveauStatut) {
        this.statut = nouveauStatut;
    }
}