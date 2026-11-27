package com.esprit.eventservice.dto;

import java.math.BigDecimal;

public record SalleResponse(
        Long id,
        String nom,
        int capacite,
        BigDecimal prix,
        Long hotelId
) {}