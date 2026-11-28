package tn.esprit.hotel.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.hotel.booking.entity.Disponibilite;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface DisponibiliteRepository extends JpaRepository<Disponibilite, UUID> {

    List<Disponibilite> findByChambreId(UUID chambreId);

    List<Disponibilite> findByReservationId(UUID reservationId);

    void deleteByReservationId(UUID reservationId);

    // période bloquée à la main (maintenance...) qui chevauche [debut, fin]
    boolean existsByChambreIdAndDisponibleFalseAndReservationIsNullAndDateDebutLessThanAndDateFinGreaterThan(
            UUID chambreId, LocalDate fin, LocalDate debut);
}