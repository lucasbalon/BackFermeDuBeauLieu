package be.technobel.backfermedubeaulieu.bll.services;


import be.technobel.backfermedubeaulieu.pl.models.dtos.searchBovin.BovinSearchDTO;
import be.technobel.backfermedubeaulieu.pl.models.forms.createBovin.BovinForm;

import java.util.ArrayList;
import java.util.List;

public interface BovinService {
    List<BovinSearchDTO> findBovinsByLoopNumber(String loopNumber);
    void createBovin(BovinForm bovinForm);
    String[] findAllBovinsLoopNumber();
    ArrayList<BovinSearchDTO> findAllBovins();
}
