package com.esprit.eventservice.entity;

import com.esprit.eventservice.enums.StatutDevis;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "devis")
@Getter
@Setter
@NoArgsConstructor
public class Devis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 10, scale = 2)
    private BigDecimal montantTotal;

    @Enumerated(EnumType.STRING)
    private StatutDevis statut = StatutDevis.EN_ATTENTE;

    private LocalDate dateValidite;

    @OneToOne(optional = false)
    @JoinColumn(name = "evenement_id", unique = true)
    private Evenement evenement;

    public void valider() {
        this.statut = StatutDevis.VALIDE;
    }
}