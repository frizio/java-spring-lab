package dev.frizio.lab.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductRequestDto(
        @NotBlank String name,
        @NotNull @PositiveOrZero Double price,
        @NotNull Boolean promo) {
}
