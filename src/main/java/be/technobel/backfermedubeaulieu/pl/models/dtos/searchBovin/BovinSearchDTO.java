package be.technobel.backfermedubeaulieu.pl.models.dtos.searchBovin;

import be.technobel.backfermedubeaulieu.dal.models.Bull;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link be.technobel.backfermedubeaulieu.dal.models.Bull}
 */
//TODO:
@Builder
public record BovinSearchDTO(
        Long id,
        String loopNumber,
        boolean gender,
        String coat,
        LocalDate birthDate,
        PastureSearchDTO pasture
){
    public static BovinSearchDTO fromEntity(Bull bull) {
        return BovinSearchDTO.builder()
                .id(bull.getId())
                .loopNumber(bull.getLoopNumber())
                .gender(bull.isGender())
                .coat(bull.getCoat())
                .birthDate(bull.getBirthDate())
                .pasture(new PastureSearchDTO(bull.getPasture().getName()))
                .build();
    }
}