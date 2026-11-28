package tn.esprit.hotel.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.hotel.booking.dto.EquipementRequest;
import tn.esprit.hotel.booking.dto.EquipementResponse;
import tn.esprit.hotel.booking.entity.Equipement;
import tn.esprit.hotel.booking.exception.ResourceNotFoundException;
import tn.esprit.hotel.booking.repository.EquipementRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class EquipementService {

    private final EquipementRepository equipementRepository;

    public EquipementResponse creer(EquipementRequest request) {
        Equipement equipement = new Equipement();
        equipement.setLibelle(request.libelle());
        return EquipementResponse.from(equipementRepository.save(equipement));
    }

    @Transactional(readOnly = true)
    public List<EquipementResponse> lister() {
        return equipementRepository.findAll().stream().map(EquipementResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public EquipementResponse trouver(UUID id) {
        return EquipementResponse.from(chercher(id));
    }

    public EquipementResponse modifier(UUID id, EquipementRequest request) {
        Equipement equipement = chercher(id);
        equipement.setLibelle(request.libelle());
        return EquipementResponse.from(equipement);
    }

    public void supprimer(UUID id) {
        equipementRepository.delete(chercher(id));
    }

    private Equipement chercher(UUID id) {
        return equipementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Équipement introuvable : " + id));
    }
}