package be.technobel.backfermedubeaulieu.pl.models.forms;

import jakarta.validation.constraints.NotBlank;

public record PastureForm(@NotBlank String name, double size) {
}