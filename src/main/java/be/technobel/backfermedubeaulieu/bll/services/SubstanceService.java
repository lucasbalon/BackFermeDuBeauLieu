package be.technobel.backfermedubeaulieu.bll.services;

import be.technobel.backfermedubeaulieu.dal.models.Substance;
import be.technobel.backfermedubeaulieu.pl.models.dtos.SubstanceDto;
import be.technobel.backfermedubeaulieu.pl.models.forms.SubstanceForm;

import java.util.List;

public interface SubstanceService {
    List<SubstanceDto> getAllSubstances();

    void saveSubstance(SubstanceForm substance);

    Substance getSubstanceByName(String name);
}
