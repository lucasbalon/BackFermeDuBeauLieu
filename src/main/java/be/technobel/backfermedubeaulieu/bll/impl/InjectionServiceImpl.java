package be.technobel.backfermedubeaulieu.bll.impl;

import be.technobel.backfermedubeaulieu.bll.services.BovinService;
import be.technobel.backfermedubeaulieu.bll.services.InjectionService;
import be.technobel.backfermedubeaulieu.bll.services.SubstanceService;
import be.technobel.backfermedubeaulieu.dal.models.Injection;
import be.technobel.backfermedubeaulieu.dal.models.Substance;
import be.technobel.backfermedubeaulieu.dal.repositories.InjectionRepository;
import be.technobel.backfermedubeaulieu.pl.models.forms.InjectionForm;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class InjectionServiceImpl implements InjectionService {
    private final InjectionRepository injectionRepository;
    private final SubstanceService substanceService;
    private final BovinService bovinService;

    public InjectionServiceImpl(InjectionRepository injectionRepository, SubstanceService substanceService, BovinService bovinService) {
        this.injectionRepository = injectionRepository;
        this.substanceService = substanceService;
        this.bovinService = bovinService;
    }

    @Override
    public void save(InjectionForm injectionForm) {
        Substance foundSubstance = substanceService.getSubstanceByName(injectionForm.substanceName());

        if (foundSubstance != null) {
            Injection injection = new Injection();
            injection.setSubstance(foundSubstance);
            injection.setInjectionDate(injectionForm.injectionDate());
            injection.setBovin(bovinService.findByLoopNumber(injectionForm.bovinLoopNumber()));

            injectionRepository.save(injection);
        } else {
            throw new EntityNotFoundException(injectionForm.substanceName());
        }
    }
}
