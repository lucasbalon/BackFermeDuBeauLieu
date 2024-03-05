package be.technobel.backfermedubeaulieu.bll.impl;

import be.technobel.backfermedubeaulieu.bll.services.BovinService;
import be.technobel.backfermedubeaulieu.dal.repositories.BovinRepository;
import be.technobel.backfermedubeaulieu.pl.models.dtos.searchBovin.BovinSearchDTO;
import be.technobel.backfermedubeaulieu.pl.models.forms.createBovin.BovinForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BovinServiceImpl implements BovinService {
    private final BovinRepository bovinRepository;

    public BovinServiceImpl(BovinRepository bovinRepository) {
        this.bovinRepository = bovinRepository;
    }

    @Override
    public List<BovinSearchDTO> findBovinsByLoopNumber(int loopNumber) {
        return bovinRepository.findBovinsByLoopNumber(loopNumber);
    }

    //TODO: Faire hériter ma cow de bull, faire un unique service, pas forcément de boolean gender, créer tout en fonction de ma classe dans mon service.

    @Override
    public void createBovin(BovinForm bovinForm) {
        //bovinRepository.save(bovinForm);
    }
}
