package be.technobel.backfermedubeaulieu.pl.models.forms.createBovin;

import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record BovinForm(
                        String loopNumber,
                        String coat,
                        boolean gender,
                        @PastOrPresent(message = "Date future impossible") LocalDate birthDate,
                        boolean cesarean,
                        String motherLoopNumber
) implements IBovinForm{
}