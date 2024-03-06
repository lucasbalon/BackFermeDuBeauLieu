package be.technobel.backfermedubeaulieu.bll.impl;

import be.technobel.backfermedubeaulieu.bll.services.BovinService;
import be.technobel.backfermedubeaulieu.dal.models.Bull;
import be.technobel.backfermedubeaulieu.dal.repositories.BullRepository;
import be.technobel.backfermedubeaulieu.dal.repositories.CowRepository;
import be.technobel.backfermedubeaulieu.pl.models.dtos.searchBovin.BovinSearchDTO;
import be.technobel.backfermedubeaulieu.pl.models.forms.createBovin.BovinForm;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BovinServiceImpl implements BovinService {
    private final BullRepository bullRepository;
    private final CowRepository cowRepository;

    public BovinServiceImpl(BullRepository bullRepository, CowRepository cowRepository) {
        this.bullRepository = bullRepository;
        this.cowRepository = cowRepository;
    }

    @Override
    public List<BovinSearchDTO> findBovinsByLoopNumber(String loopNumber) {
        ArrayList<BovinSearchDTO> bovinSearchDTOs = new ArrayList<>(bullRepository.findAllByLoopNumber(loopNumber).stream().map(BovinSearchDTO::fromEntity).toList());
        bovinSearchDTOs.addAll(cowRepository.findAllByLoopNumber(loopNumber).stream().map(BovinSearchDTO::fromEntity).toList());
        return bovinSearchDTOs;
    }

    //TODO: Faire hériter ma cow de bull, faire un unique service, pas forcément de boolean gender, créer tout en fonction de ma classe dans mon service.

    @Override
    public void createBovin(BovinForm bovinForm) {
        //Si c'est un male
        if (bovinForm.gender()) {
            bullRepository.save(Bull.builder()
                    .coat(bovinForm.coat())
                    .birthDate(bovinForm.birthDate())
                    .cesarean(bovinForm.cesarean())
                    .mother(cowRepository.findByLoopNumber(bovinForm.motherLoopNumber()).orElseThrow(() -> new EntityNotFoundException("Meuh meuh pas trouvé"))).build());
        }
    }
}
