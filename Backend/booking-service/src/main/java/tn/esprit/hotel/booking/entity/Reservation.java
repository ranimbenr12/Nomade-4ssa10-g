package tn.esprit.hotel.booking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.hotel.booking.enums.StatutReservation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // liens par id vers les autres services (pas de clé étrangère)
    private UUID clientId;
    private UUID paymentId; // nullable

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "chambre_id")
    private Chambre chambre;

    private UUID hotelId;

    private LocalDate dateDebut;
    private LocalDate dateFin;
    private int nbPersonnes;

    @Column(precision = 10, scale = 2)
    private BigDecimal montantTotal;

    @Enumerated(EnumType.STRING)
    private StatutReservation statut = StatutReservation.EN_ATTENTE;

    private LocalDateTime dateCreation;

    @PrePersist
    void avantCreation() {
        dateCreation = LocalDateTime.now();
    }

    public void confirmer() {
        this.statut = StatutReservation.CONFIRMEE;
    }

    public void annuler() {
        this.statut = StatutReservation.ANNULEE;
    }
}