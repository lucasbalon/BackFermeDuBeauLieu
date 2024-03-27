package be.technobel.backfermedubeaulieu.pl.models.dtos;

import be.technobel.backfermedubeaulieu.dal.models.Substance;
import jakarta.validation.constraints.NotBlank;

public record SubstanceDto(@NotBlank String name) {
    public static SubstanceDto fromEntity(Substance substance) {
        return new SubstanceDto(substance.getName());
    }
}