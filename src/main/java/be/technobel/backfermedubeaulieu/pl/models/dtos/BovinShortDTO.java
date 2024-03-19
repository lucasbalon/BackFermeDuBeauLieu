package be.technobel.backfermedubeaulieu.pl.models.dtos;


import be.technobel.backfermedubeaulieu.dal.models.Bull;

public record BovinShortDTO(Long id, String loopNumber){

    public static BovinShortDTO fromEntity(Bull bull) {
        return new BovinShortDTO(bull.getId(), bull.getLoopNumber());
    }
}