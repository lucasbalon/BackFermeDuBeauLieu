package be.technobel.backfermedubeaulieu.pl.models.forms.createBovin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;


public record ShortBovinForm(
        @Size(message = "Numéro de boucle: 4charactères requis", min = 4, max = 4)
        String loopNumber,
        @NotBlank(message = "Robe: champ obligatoire")
        String coat,
        boolean gender,
        @PastOrPresent
        LocalDate birthDate) implements IBovinForm {


}