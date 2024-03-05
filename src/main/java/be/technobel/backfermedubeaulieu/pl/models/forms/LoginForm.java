package be.technobel.backfermedubeaulieu.pl.models.forms;

import jakarta.validation.constraints.NotBlank;

public record LoginForm(
        @NotBlank
        String login,
        @NotBlank
        String password
) {
}