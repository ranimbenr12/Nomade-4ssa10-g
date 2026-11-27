package com.esprit.eventservice.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record SalleRequest(
        @NotBlank String nom,
        @Min(1) int capacite,
        @NotNull @PositiveOrZero BigDecimal prix,
        Long hotelId
) {}