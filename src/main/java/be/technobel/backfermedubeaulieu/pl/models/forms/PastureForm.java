package be.technobel.backfermedubeaulieu.pl.models.forms;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record PastureForm(@NotBlank String name, double size) implements Serializable {
}