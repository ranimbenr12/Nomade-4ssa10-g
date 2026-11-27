package com.esprit.eventservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "salles")
@Getter
@Setter
@NoArgsConstructor
public class Salle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private int capacite;

    @Column(precision = 10, scale = 2)
    private BigDecimal prix;

    // Référence vers le Hotel, géré par un autre microservice : pas de relation JPA
    private Long hotelId;

    public boolean estDisponible(LocalDate date) {
        // À compléter plus tard avec les réservations (Booking Service)
        return true;
    }
}