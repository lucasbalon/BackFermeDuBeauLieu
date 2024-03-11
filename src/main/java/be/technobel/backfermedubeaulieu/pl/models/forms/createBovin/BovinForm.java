package be.technobel.backfermedubeaulieu.pl.models.forms.createBovin;

import be.technobel.backfermedubeaulieu.dal.models.Bull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link be.technobel.backfermedubeaulieu.dal.models.Bull}
 */
public record BovinForm(
                        String loopNumber,
                        String coat,
                        boolean gender,
                        @PastOrPresent(message = "Date future impossible") LocalDate birthDate,
                        boolean cesarean,
                        String motherLoopNumber
) implements Serializable {
}