package com.esprit.eventservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "options")
@Getter
@Setter
@NoArgsConstructor
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    @Column(precision = 10, scale = 2)
    private BigDecimal prix;

    @ManyToOne(optional = false)
    @JoinColumn(name = "evenement_id")
    private Evenement evenement;

    // Référence vers le service de prestation, géré ailleurs
    private Long prestataireId;

    public BigDecimal calculerPrix() {
        return prix;
    }
}