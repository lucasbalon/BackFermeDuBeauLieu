package be.technobel.backfermedubeaulieu.pl.models.forms;

import jakarta.validation.constraints.NotBlank;

public record SubstanceForm(@NotBlank String name){
}