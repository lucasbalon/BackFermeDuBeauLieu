package be.technobel.backfermedubeaulieu.bll.services;

import be.technobel.backfermedubeaulieu.dal.models.Bull;
import be.technobel.backfermedubeaulieu.dal.models.Pasture;
import be.technobel.backfermedubeaulieu.pl.models.dtos.PastureDto;
import be.technobel.backfermedubeaulieu.pl.models.forms.PastureForm;

import java.util.List;

public interface PastureService {
    List<PastureDto> getAllPastures();

    void savePasture(PastureForm pastureForm);

    Pasture findByName(String name);
}
