package be.technobel.backfermedubeaulieu.pl.models.dtos.searchBovin;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link be.technobel.backfermedubeaulieu.dal.models.Bovin}
 */
public record BovinSearchDTO(
        Long id,
        int loopNumber,
        boolean gender,
        String coat,
        LocalDate birthDate,
        PastureSearchDTO pasture
) implements Serializable {
}