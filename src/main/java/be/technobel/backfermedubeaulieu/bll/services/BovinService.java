package be.technobel.backfermedubeaulieu.bll.services;


import be.technobel.backfermedubeaulieu.pl.models.dtos.searchBovin.BovinSearchDTO;

import java.util.List;

public interface BovinService {
    public List<BovinSearchDTO> findBovinByLoopNumber(int loopNumber);
}
