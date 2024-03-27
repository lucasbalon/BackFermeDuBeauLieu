package be.technobel.backfermedubeaulieu.pl.models.dtos;

import be.technobel.backfermedubeaulieu.dal.models.Pasture;

/**
 * DTO for {@link be.technobel.backfermedubeaulieu.dal.models.Pasture}
 */
public record PastureDto(long id, String name, double size) {
    public static PastureDto fromEntity(Pasture pasture) {
        return new PastureDto(pasture.getId(), pasture.getName(), pasture.getSize());
    }
}