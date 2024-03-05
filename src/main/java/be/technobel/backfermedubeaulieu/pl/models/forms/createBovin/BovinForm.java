package be.technobel.backfermedubeaulieu.pl.models.forms.createBovin;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link be.technobel.backfermedubeaulieu.dal.models.Bovin}
 */
public record BovinForm(@Min(message = "Trop court", value = 4)
                        @Max(message = "Trop long", value = 4)
                        int loopNumber,
                        String coat,
                        boolean gender,
                        @PastOrPresent(message = "Date future impossible") LocalDate birthDate,
                        boolean cesarean,
                        CowBovinForm mother
) implements Serializable {
}