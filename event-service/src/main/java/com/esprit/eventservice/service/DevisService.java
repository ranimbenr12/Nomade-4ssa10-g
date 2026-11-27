package com.esprit.eventservice.service;

import com.esprit.eventservice.dto.DevisResponse;
import com.esprit.eventservice.entity.Devis;
import com.esprit.eventservice.exception.BusinessException;
import com.esprit.eventservice.exception.ResourceNotFoundException;
import com.esprit.eventservice.mapper.DevisMapper;
import com.esprit.eventservice.repository.DevisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DevisService {

    private final DevisRepository devisRepository;

    @Transactional(readOnly = true)
    public DevisResponse trouver(Long id) {
        Devis devis = devisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Devis", id));
        return DevisMapper.toResponse(devis);
    }

    @Transactional
    public DevisResponse valider(Long id) {
        Devis devis = devisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Devis", id));

        if (devis.getDateValidite().isBefore(LocalDate.now())) {
            throw new BusinessException("Ce devis a expiré");
        }

        devis.valider();
        return DevisMapper.toResponse(devisRepository.save(devis));
    }
}