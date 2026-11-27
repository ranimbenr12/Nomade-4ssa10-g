package com.esprit.eventservice.service;

import com.esprit.eventservice.dto.SalleRequest;
import com.esprit.eventservice.dto.SalleResponse;
import com.esprit.eventservice.entity.Salle;
import com.esprit.eventservice.exception.ResourceNotFoundException;
import com.esprit.eventservice.mapper.SalleMapper;
import com.esprit.eventservice.repository.SalleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalleService {

    private final SalleRepository salleRepository;

    @Transactional
    public SalleResponse creer(SalleRequest request) {
        Salle salle = salleRepository.save(SalleMapper.toEntity(request));
        return SalleMapper.toResponse(salle);
    }

    @Transactional(readOnly = true)
    public List<SalleResponse> lister() {
        return salleRepository.findAll().stream()
                .map(SalleMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public SalleResponse trouver(Long id) {
        Salle salle = salleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Salle", id));
        return SalleMapper.toResponse(salle);
    }
}