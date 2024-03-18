package be.technobel.backfermedubeaulieu.pl.models.dtos;

import be.technobel.backfermedubeaulieu.dal.models.Pasture;

import java.io.Serializable;

/**
 * DTO for {@link be.technobel.backfermedubeaulieu.dal.models.Pasture}
 */
public record PastureDto(String name, double size){
  public static PastureDto fromEntity(Pasture pasture) {
    return new PastureDto(pasture.getName(), pasture.getSize());
  }
}