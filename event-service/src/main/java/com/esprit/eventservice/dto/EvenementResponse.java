package com.esprit.eventservice.dto;

import com.esprit.eventservice.enums.StatutEvenement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record EvenementResponse(
        Long id,
        String type,
        LocalDate date,
        int nbInvites,
        StatutEvenement statut,
        Long clientId,
        Long salleId,
        List<OptionResponse> options,
        BigDecimal total
) {}