package com.esprit.eventservice.service;

import com.esprit.eventservice.dto.EvenementRequest;
import com.esprit.eventservice.dto.EvenementResponse;
import com.esprit.eventservice.dto.OptionRequest;
import com.esprit.eventservice.entity.Devis;
import com.esprit.eventservice.entity.Evenement;
import com.esprit.eventservice.entity.Option;
import com.esprit.eventservice.entity.Salle;
import com.esprit.eventservice.enums.StatutEvenement;
import com.esprit.eventservice.exception.BusinessException;
import com.esprit.eventservice.exception.ResourceNotFoundException;
import com.esprit.eventservice.mapper.EvenementMapper;
import com.esprit.eventservice.mapper.OptionMapper;
import com.esprit.eventservice.repository.EvenementRepository;
import com.esprit.eventservice.repository.SalleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EvenementService {

    private final EvenementRepository evenementRepository;
    private final SalleRepository salleRepository;

    @Transactional
    public EvenementResponse creer(EvenementRequest request) {
        Evenement evenement = EvenementMapper.toEntity(request);

        // La salle est optionnelle : un événement peut se dérouler hors salle
        if (request.salleId() != null) {
            Salle salle = salleRepository.findById(request.salleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Salle", request.salleId()));
            evenement.setSalle(salle);
        }

        return EvenementMapper.toResponse(evenementRepository.save(evenement));
    }

    @Transactional(readOnly = true)
    public List<EvenementResponse> lister() {
        return evenementRepository.findAll().stream()
                .map(EvenementMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public EvenementResponse trouver(Long id) {
        return EvenementMapper.toResponse(chercher(id));
    }

    @Transactional
    public EvenementResponse ajouterOption(Long id, OptionRequest request) {
        Evenement evenement = chercher(id);

        if (evenement.getStatut() == StatutEvenement.PAYE
                || evenement.getStatut() == StatutEvenement.ANNULE) {
            throw new BusinessException("Impossible d'ajouter une option à un événement " + evenement.getStatut());
        }

        Option option = OptionMapper.toEntity(request);
        evenement.ajouterOption(option);

        // Pas besoin de sauvegarder l'option : cascade = ALL sur Evenement.options
        return EvenementMapper.toResponse(evenementRepository.save(evenement));
    }

    @Transactional
    public EvenementResponse changerStatut(Long id, StatutEvenement nouveauStatut) {
        Evenement evenement = chercher(id);

        if (evenement.getStatut() == StatutEvenement.ANNULE) {
            throw new BusinessException("Un événement annulé ne peut plus changer de statut");
        }

        evenement.changerStatut(nouveauStatut);
        return EvenementMapper.toResponse(evenementRepository.save(evenement));
    }

    @Transactional
    public EvenementResponse genererDevis(Long id) {
        Evenement evenement = chercher(id);

        if (evenement.getDevis() != null) {
            throw new BusinessException("Un devis existe déjà pour cet événement");
        }

        Devis devis = new Devis();
        devis.setMontantTotal(evenement.calculerTotal());
        devis.setDateValidite(LocalDate.now().plusDays(30));
        devis.setEvenement(evenement);
        evenement.setDevis(devis);

        // cascade = ALL sur Evenement.devis : le devis est sauvegardé avec l'événement
        return EvenementMapper.toResponse(evenementRepository.save(evenement));
    }

    // Méthode interne, réutilisée par les autres méthodes du service
    private Evenement chercher(Long id) {
        return evenementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Événement", id));
    }
}