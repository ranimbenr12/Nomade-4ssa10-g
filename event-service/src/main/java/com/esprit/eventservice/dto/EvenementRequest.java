package com.esprit.eventservice.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record EvenementRequest(
        @NotBlank String type,
        @NotNull LocalDate date,
        @Min(0) int nbInvites,
        @NotNull Long clientId,
        Long salleId
) {}