package tn.esprit.hotel.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.hotel.booking.dto.ReservationRequest;
import tn.esprit.hotel.booking.dto.ReservationResponse;
import tn.esprit.hotel.booking.entity.Chambre;
import tn.esprit.hotel.booking.entity.Disponibilite;
import tn.esprit.hotel.booking.entity.Reservation;
import tn.esprit.hotel.booking.enums.StatutReservation;
import tn.esprit.hotel.booking.exception.ConflictException;
import tn.esprit.hotel.booking.exception.ResourceNotFoundException;
import tn.esprit.hotel.booking.repository.ChambreRepository;
import tn.esprit.hotel.booking.repository.DisponibiliteRepository;
import tn.esprit.hotel.booking.repository.ReservationRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    private static final List<StatutReservation> STATUTS_ACTIFS = List.of(
            StatutReservation.EN_ATTENTE, StatutReservation.CONFIRMEE, StatutReservation.EN_COURS);

    private final ReservationRepository reservationRepository;
    private final ChambreRepository chambreRepository;
    private final DisponibiliteRepository disponibiliteRepository;

    public ReservationResponse creer(ReservationRequest request) {
        Chambre chambre = chambreRepository.findById(request.chambreId())
                .orElseThrow(() -> new ResourceNotFoundException("Chambre introuvable : " + request.chambreId()));

        verifierDates(request.dateDebut(), request.dateFin());
        verifierCapacite(chambre, request.nbPersonnes());
        verifierPeriodeLibre(chambre, request.dateDebut(), request.dateFin(), null);

        Reservation reservation = new Reservation();
        reservation.setClientId(request.clientId());
        reservation.setPaymentId(request.paymentId());
        reservation.setChambre(chambre);
        reservation.setHotelId(chambre.getHotel().getId());
        reservation.setDateDebut(request.dateDebut());
        reservation.setDateFin(request.dateFin());
        reservation.setNbPersonnes(request.nbPersonnes());
        reservation.setMontantTotal(calculerMontant(chambre, request.dateDebut(), request.dateFin()));
        reservation.setStatut(StatutReservation.EN_ATTENTE);
        Reservation saved = reservationRepository.save(reservation);

        // la réservation bloque la période dans le planning de la chambre
        Disponibilite blocage = new Disponibilite();
        blocage.setChambre(chambre);
        blocage.setReservation(saved);
        blocage.setDateDebut(request.dateDebut());
        blocage.setDateFin(request.dateFin());
        blocage.setDisponible(false);
        disponibiliteRepository.save(blocage);

        return ReservationResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public List<ReservationResponse> lister(UUID clientId, UUID chambreId) {
        List<Reservation> reservations;
        if (clientId != null) {
            reservations = reservationRepository.findByClientId(clientId);
        } else if (chambreId != null) {
            reservations = reservationRepository.findByChambreId(chambreId);
        } else {
            reservations = reservationRepository.findAll();
        }
        return reservations.stream().map(ReservationResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public ReservationResponse trouver(UUID id) {
        return ReservationResponse.from(chercher(id));
    }

    // la chambre et le client d'une réservation ne changent pas : seuls dates, personnes et paiement sont modifiables
    public ReservationResponse modifier(UUID id, ReservationRequest request) {
        Reservation reservation = chercher(id);
        if (reservation.getStatut() != StatutReservation.EN_ATTENTE) {
            throw new ConflictException("Seule une réservation EN_ATTENTE peut être modifiée");
        }
        Chambre chambre = reservation.getChambre();
        verifierDates(request.dateDebut(), request.dateFin());
        verifierCapacite(chambre, request.nbPersonnes());
        verifierPeriodeLibre(chambre, request.dateDebut(), request.dateFin(), id);

        reservation.setDateDebut(request.dateDebut());
        reservation.setDateFin(request.dateFin());
        reservation.setNbPersonnes(request.nbPersonnes());
        reservation.setPaymentId(request.paymentId());
        reservation.setMontantTotal(calculerMontant(chambre, request.dateDebut(), request.dateFin()));

        disponibiliteRepository.findByReservationId(id).forEach(d -> {
            d.setDateDebut(request.dateDebut());
            d.setDateFin(request.dateFin());
        });
        return ReservationResponse.from(reservation);
    }

    public ReservationResponse confirmer(UUID id) {
        Reservation reservation = chercher(id);
        exigerStatut(reservation, "confirmée", StatutReservation.EN_ATTENTE);
        reservation.confirmer();
        return ReservationResponse.from(reservation);
    }

    public ReservationResponse annuler(UUID id) {
        Reservation reservation = chercher(id);
        exigerStatut(reservation, "annulée", StatutReservation.EN_ATTENTE, StatutReservation.CONFIRMEE);
        reservation.annuler();
        disponibiliteRepository.deleteByReservationId(id); // la période est libérée
        return ReservationResponse.from(reservation);
    }

    public ReservationResponse demarrer(UUID id) {
        Reservation reservation = chercher(id);
        exigerStatut(reservation, "démarrée", StatutReservation.CONFIRMEE);
        reservation.setStatut(StatutReservation.EN_COURS);
        return ReservationResponse.from(reservation);
    }

    public ReservationResponse terminer(UUID id) {
        Reservation reservation = chercher(id);
        exigerStatut(reservation, "terminée", StatutReservation.EN_COURS);
        reservation.setStatut(StatutReservation.TERMINEE);
        return ReservationResponse.from(reservation);
    }

    public void supprimer(UUID id) {
        Reservation reservation = chercher(id);
        disponibiliteRepository.deleteByReservationId(id);
        reservationRepository.delete(reservation);
    }

    private Reservation chercher(UUID id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Réservation introuvable : " + id));
    }

    private void exigerStatut(Reservation reservation, String action, StatutReservation... autorises) {
        for (StatutReservation statut : autorises) {
            if (reservation.getStatut() == statut) {
                return;
            }
        }
        throw new ConflictException("La réservation ne peut pas être " + action
                + " (statut actuel : " + reservation.getStatut() + ")");
    }

    private void verifierDates(LocalDate debut, LocalDate fin) {
        if (!fin.isAfter(debut)) {
            throw new IllegalArgumentException("dateFin doit être après dateDebut");
        }
        if (debut.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("dateDebut ne peut pas être dans le passé");
        }
    }

    private void verifierCapacite(Chambre chambre, int nbPersonnes) {
        if (nbPersonnes > chambre.getCapacite()) {
            throw new IllegalArgumentException(
                    "La chambre accepte au maximum " + chambre.getCapacite() + " personne(s)");
        }
    }

    private void verifierPeriodeLibre(Chambre chambre, LocalDate debut, LocalDate fin, UUID reservationExclue) {
        if (!chambre.isActive()) {
            throw new ConflictException("La chambre est désactivée");
        }
        boolean reserveeParUneAutre = (reservationExclue == null)
                ? reservationRepository.existeChevauchement(chambre.getId(), debut, fin, STATUTS_ACTIFS)
                : reservationRepository.existeChevauchementHors(
                chambre.getId(), debut, fin, STATUTS_ACTIFS, reservationExclue);
        boolean bloqueeManuellement = disponibiliteRepository
                .existsByChambreIdAndDisponibleFalseAndReservationIsNullAndDateDebutLessThanAndDateFinGreaterThan(
                        chambre.getId(), fin, debut);
        if (reserveeParUneAutre || bloqueeManuellement) {
            throw new ConflictException("La chambre n'est pas disponible sur cette période");
        }
    }

    private BigDecimal calculerMontant(Chambre chambre, LocalDate debut, LocalDate fin) {
        long nuits = ChronoUnit.DAYS.between(debut, fin);
        return chambre.getPrixParNuit().multiply(BigDecimal.valueOf(nuits));
    }
}