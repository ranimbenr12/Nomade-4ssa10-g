package tn.esprit.hotel.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.hotel.booking.dto.ChambreRequest;
import tn.esprit.hotel.booking.dto.ChambreResponse;
import tn.esprit.hotel.booking.entity.Chambre;
import tn.esprit.hotel.booking.entity.Equipement;
import tn.esprit.hotel.booking.entity.Hotel;
import tn.esprit.hotel.booking.exception.ConflictException;
import tn.esprit.hotel.booking.exception.ResourceNotFoundException;
import tn.esprit.hotel.booking.repository.ChambreRepository;
import tn.esprit.hotel.booking.repository.EquipementRepository;
import tn.esprit.hotel.booking.repository.HotelRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ChambreService {

    private final ChambreRepository chambreRepository;
    private final HotelRepository hotelRepository;
    private final EquipementRepository equipementRepository;

    public ChambreResponse creer(ChambreRequest request) {
        Hotel hotel = hotelRepository.findById(request.hotelId())
                .orElseThrow(() -> new ResourceNotFoundException("Hôtel introuvable : " + request.hotelId()));
        if (chambreRepository.existsByHotelIdAndNumero(hotel.getId(), request.numero())) {
            throw new ConflictException("Le numéro " + request.numero() + " existe déjà dans cet hôtel");
        }
        Chambre chambre = new Chambre();
        chambre.setHotel(hotel);
        appliquer(chambre, request);
        return ChambreResponse.from(chambreRepository.save(chambre));
    }

    @Transactional(readOnly = true)
    public List<ChambreResponse> lister(UUID hotelId) {
        List<Chambre> chambres = (hotelId == null)
                ? chambreRepository.findAll()
                : chambreRepository.findByHotelId(hotelId);
        return chambres.stream().map(ChambreResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public ChambreResponse trouver(UUID id) {
        return ChambreResponse.from(chercher(id));
    }

    public ChambreResponse modifier(UUID id, ChambreRequest request) {
        Chambre chambre = chercher(id);
        boolean numeroChange = !chambre.getNumero().equals(request.numero());
        if (numeroChange && chambreRepository.existsByHotelIdAndNumero(
                chambre.getHotel().getId(), request.numero())) {
            throw new ConflictException("Le numéro " + request.numero() + " existe déjà dans cet hôtel");
        }
        // l'hôtel d'une chambre ne change pas : hotelId est ignoré à la modification
        appliquer(chambre, request);
        return ChambreResponse.from(chambre);
    }

    public void supprimer(UUID id) {
        chambreRepository.delete(chercher(id));
    }

    private Chambre chercher(UUID id) {
        return chambreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Chambre introuvable : " + id));
    }

    private void appliquer(Chambre chambre, ChambreRequest r) {
        chambre.setNumero(r.numero());
        chambre.setType(r.type());
        chambre.setCapacite(r.capacite());
        chambre.setPrixParNuit(r.prixParNuit());
        if (r.active() != null) {
            chambre.setActive(r.active());
        }
        chambre.setEquipements(chargerEquipements(r.equipementIds()));
    }

    private Set<Equipement> chargerEquipements(Set<UUID> ids) {
        if (ids == null || ids.isEmpty()) {
            return new HashSet<>();
        }
        List<Equipement> trouves = equipementRepository.findAllById(ids);
        if (trouves.size() != ids.size()) {
            throw new ResourceNotFoundException("Un ou plusieurs équipements sont introuvables");
        }
        return new HashSet<>(trouves);
    }
}