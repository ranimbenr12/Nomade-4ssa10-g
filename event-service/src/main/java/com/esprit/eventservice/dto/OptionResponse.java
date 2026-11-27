package com.esprit.eventservice.dto;

import java.math.BigDecimal;

public record OptionResponse(
        Long id,
        String type,
        BigDecimal prix,
        Long prestataireId
) {}