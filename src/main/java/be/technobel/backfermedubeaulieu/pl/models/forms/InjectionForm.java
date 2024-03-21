package be.technobel.backfermedubeaulieu.pl.models.forms;

import be.technobel.backfermedubeaulieu.dal.models.Injection;
import jakarta.validation.constraints.PastOrPresent;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link be.technobel.backfermedubeaulieu.dal.models.Injection}
 */
public record InjectionForm(
        @PastOrPresent(message = "Injection future impossible") LocalDate injectionDate,
        String bovinLoopNumber,
        String substanceName
) {
}