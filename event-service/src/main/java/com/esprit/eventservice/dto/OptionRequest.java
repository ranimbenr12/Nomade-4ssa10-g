package com.esprit.eventservice.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record OptionRequest(
        @NotBlank String type,
        @NotNull @PositiveOrZero BigDecimal prix,
        Long prestataireId
) {}