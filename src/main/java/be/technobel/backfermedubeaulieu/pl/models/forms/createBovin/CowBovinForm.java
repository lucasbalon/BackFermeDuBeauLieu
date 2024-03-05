package be.technobel.backfermedubeaulieu.pl.models.forms.createBovin;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.io.Serializable;

/**
 * DTO for {@link be.technobel.backfermedubeaulieu.dal.models.Cow}
 */
public record CowBovinForm(
        @Min(message = "Trop court", value = 4) @Max(message = "Trop long", value = 4) int loopNumber) implements Serializable {
}