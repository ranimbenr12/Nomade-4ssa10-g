package tn.esprit.hotel.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.hotel.booking.entity.Reservation;
import tn.esprit.hotel.booking.enums.StatutReservation;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    List<Reservation> findByClientId(UUID clientId);

    List<Reservation> findByChambreId(UUID chambreId);

    // vrai si la chambre a déjà une réservation qui chevauche la période demandée
    @Query("""
            select count(r) > 0 from Reservation r
            where r.chambre.id = :chambreId
              and r.statut in :statuts
              and r.dateDebut < :fin
              and r.dateFin > :debut
            """)
    boolean existeChevauchement(@Param("chambreId") UUID chambreId,
                                @Param("debut") LocalDate debut,
                                @Param("fin") LocalDate fin,
                                @Param("statuts") List<StatutReservation> statuts);
    // même contrôle, en ignorant la réservation en cours de modification
    @Query("""
            select count(r) > 0 from Reservation r
            where r.chambre.id = :chambreId
              and r.id <> :reservationId
              and r.statut in :statuts
              and r.dateDebut < :fin
              and r.dateFin > :debut
            """)
    boolean existeChevauchementHors(@Param("chambreId") UUID chambreId,
                                    @Param("debut") LocalDate debut,
                                    @Param("fin") LocalDate fin,
                                    @Param("statuts") List<StatutReservation> statuts,
                                    @Param("reservationId") UUID reservationId);

}