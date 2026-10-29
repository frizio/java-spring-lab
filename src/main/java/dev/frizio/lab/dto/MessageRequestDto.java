package dev.frizio.lab.dto;

import jakarta.validation.constraints.NotBlank;

public record MessageRequestDto(@NotBlank String message) {
}
