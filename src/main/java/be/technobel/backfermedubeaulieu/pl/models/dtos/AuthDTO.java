package be.technobel.backfermedubeaulieu.pl.models.dtos;

import lombok.Builder;

@Builder
public record AuthDTO(
        String login,
        String token
) {
}