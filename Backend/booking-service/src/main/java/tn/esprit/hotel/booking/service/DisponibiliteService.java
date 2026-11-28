package tn.esprit.hotel.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.hotel.booking.dto.DisponibiliteRequest;
import tn.esprit.hotel.booking.dto.DisponibiliteResponse;
import tn.esprit.hotel.booking.entity.Chambre;
import tn.esprit.hotel.booking.entity.Disponibilite;
import tn.esprit.hotel.booking.enums.StatutReservation;
import tn.esprit.hotel.booking.exception.ConflictException;
import tn.esprit.hotel.booking.exception.ResourceNotFoundException;
import tn.esprit.hotel.booking.repository.ChambreRepository;
import tn.esprit.hotel.booking.repository.DisponibiliteRepository;
import tn.esprit.hotel.booking.repository.ReservationRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class DisponibiliteService {

    private static final List<StatutReservation> STATUTS_ACTIFS = List.of(
            StatutReservation.EN_ATTENTE, StatutReservation.CONFIRMEE, StatutReservation.EN_COURS);

    private final DisponibiliteRepository disponibiliteRepository;
    private final ChambreRepository chambreRepository;
    private final ReservationRepository reservationRepository;

    public DisponibiliteResponse creer(DisponibiliteRequest request) {
        Chambre chambre = chambreRepository.findById(request.chambreId())
                .orElseThrow(() -> new ResourceNotFoundException("Chambre introuvable : " + request.chambreId()));
        controler(chambre, request);

        Disponibilite d = new Disponibilite();
        d.setChambre(chambre);
        appliquer(d, request);
        return DisponibiliteResponse.from(disponibiliteRepository.save(d));
    }

    @Transactional(readOnly = true)
    public List<DisponibiliteResponse> lister(UUID chambreId) {
        List<Disponibilite> liste = (chambreId == null)
                ? disponibiliteRepository.findAll()
                : disponibiliteRepository.findByChambreId(chambreId);
        return liste.stream().map(DisponibiliteResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public DisponibiliteResponse trouver(UUID id) {
        return DisponibiliteResponse.from(chercher(id));
    }

    public DisponibiliteResponse modifier(UUID id, DisponibiliteRequest request) {
        Disponibilite d = chercher(id);
        interdireSiReservation(d);
        controler(d.getChambre(), request);
        appliquer(d, request);
        return DisponibiliteResponse.from(d);
    }

    public void supprimer(UUID id) {
        Disponibilite d = chercher(id);
        interdireSiReservation(d);
        disponibiliteRepository.delete(d);
    }

    @Transactional(readOnly = true)
    public boolean verifier(UUID chambreId, LocalDate debut, LocalDate fin) {
        if (!fin.isAfter(debut)) {
            throw new IllegalArgumentException("fin doit être après debut");
        }
        Chambre chambre = chambreRepository.findById(chambreId)
                .orElseThrow(() -> new ResourceNotFoundException("Chambre introuvable : " + chambreId));
        return chambre.verifierDisponibilite(debut, fin);
    }

    private Disponibilite chercher(UUID id) {
        return disponibiliteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Disponibilité introuvable : " + id));
    }

    private void interdireSiReservation(Disponibilite d) {
        if (d.getReservation() != null) {
            throw new ConflictException("Cette période est gérée par une réservation : modifiez ou annulez la réservation");
        }
    }

    private void controler(Chambre chambre, DisponibiliteRequest request) {
        if (!request.dateFin().isAfter(request.dateDebut())) {
            throw new IllegalArgumentException("dateFin doit être après dateDebut");
        }
        if (!request.disponible() && reservationRepository.existeChevauchement(
                chambre.getId(), request.dateDebut(), request.dateFin(), STATUTS_ACTIFS)) {
            throw new ConflictException("Une réservation existe déjà sur cette période");
        }
    }

    private void appliquer(Disponibilite d, DisponibiliteRequest request) {
        d.setDateDebut(request.dateDebut());
        d.setDateFin(request.dateFin());
        d.setDisponible(request.disponible());
    }
}