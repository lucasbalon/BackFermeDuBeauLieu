package be.technobel.backfermedubeaulieu.pl.models.forms.createBovin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;


public record ShortBovinForm(
        @Size(message = "Numéro de boucle: 4charactères requis", min = 4, max = 4)
        String loopNumber,
        @NotBlank(message = "Robe: champ obligatoire")
        String coat,
        boolean gender,
        @Past
        LocalDate birthDate) implements IBovinForm{


}