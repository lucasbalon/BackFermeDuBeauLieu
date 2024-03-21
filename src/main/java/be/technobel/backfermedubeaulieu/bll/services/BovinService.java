package be.technobel.backfermedubeaulieu.bll.services;


import be.technobel.backfermedubeaulieu.dal.models.Bull;
import be.technobel.backfermedubeaulieu.pl.models.dtos.BovinDto;
import be.technobel.backfermedubeaulieu.pl.models.dtos.BovinShortDTO;
import be.technobel.backfermedubeaulieu.pl.models.dtos.PastureFullDTO;
import be.technobel.backfermedubeaulieu.pl.models.dtos.searchBovin.BovinSearchDTO;
import be.technobel.backfermedubeaulieu.pl.models.forms.PastureForm;
import be.technobel.backfermedubeaulieu.pl.models.forms.createBovin.BovinForm;
import be.technobel.backfermedubeaulieu.pl.models.forms.createBovin.ShortBovinForm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface BovinService {
    List<BovinSearchDTO> findBovinsByLoopNumber(String loopNumber);
    void createBovin(BovinForm bovinForm);
    void shortCreateBovin(ShortBovinForm shortBovinForm);
    String[] findAllBovinsLoopNumber();
    String[] findAllCowLoopNumber();
    String[] findAllBullLoopNumber();
    String findBullLoopnumberByPastureId(long id);
    List<BovinSearchDTO> findAllBovins();
    BovinDto findBovinById(Long id);
    void updatePasture(Long pastureId, String cowLoopnumber);
    void updatePastureBull(Long pastureId, String bullLoopnumber);
    PastureFullDTO findPasture(long id);
    List<BovinShortDTO> findAvailableBull();
    void removeCowFromPasture(String loopNumber);
    Bull findByLoopNumber(String loopNumber);

    List<Bull> findAllBullsByPastureName(String name);
}
