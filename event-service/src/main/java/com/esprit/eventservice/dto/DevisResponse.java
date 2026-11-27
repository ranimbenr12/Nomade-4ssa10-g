package com.esprit.eventservice.dto;

import com.esprit.eventservice.enums.StatutDevis;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DevisResponse(
        Long id,
        BigDecimal montantTotal,
        StatutDevis statut,
        LocalDate dateValidite,
        Long evenementId
) {}