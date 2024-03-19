package be.technobel.backfermedubeaulieu.pl.models.dtos;

import java.util.List;

public record PastureFullDTO(
        String name,
        String actualBull,
        List<BovinShortDTO> availableCows,
        List<BovinShortDTO> pastureCows
){
}