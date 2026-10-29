package dev.frizio.lab.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BookRequestDto(
        @NotBlank String title,
        @NotBlank String author,
        @NotNull @Min(1) @Max(9999) Integer year) {
}
