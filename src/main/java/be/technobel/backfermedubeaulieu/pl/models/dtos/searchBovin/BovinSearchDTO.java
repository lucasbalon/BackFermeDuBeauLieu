package be.technobel.backfermedubeaulieu.pl.models.dtos.searchBovin;

import be.technobel.backfermedubeaulieu.dal.models.Bull;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDate;

@Builder
public record BovinSearchDTO(
        Long id,
        String loopNumber,
        boolean gender,
        String coat,
        LocalDate birthDate,
        String pasture
){
    public static BovinSearchDTO fromEntity(Bull bull) {
        return BovinSearchDTO.builder()
                .id(bull.getId())
                .loopNumber(bull.getLoopNumber())
                .gender(bull.isGender())
                .coat(bull.getCoat())
                .birthDate(bull.getBirthDate())
                .pasture(bull.getPasture() != null ? bull.getPasture().getName() : "Pature non définie")
                .build();
    }
}