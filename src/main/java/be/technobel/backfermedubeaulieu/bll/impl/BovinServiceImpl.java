package be.technobel.backfermedubeaulieu.bll.impl;

import be.technobel.backfermedubeaulieu.bll.services.BovinService;
import be.technobel.backfermedubeaulieu.dal.models.Bull;
import be.technobel.backfermedubeaulieu.dal.models.Cow;
import be.technobel.backfermedubeaulieu.dal.models.enums.Status;
import be.technobel.backfermedubeaulieu.dal.repositories.BullRepository;
import be.technobel.backfermedubeaulieu.dal.repositories.CowRepository;
import be.technobel.backfermedubeaulieu.dal.repositories.PastureRepository;
import be.technobel.backfermedubeaulieu.pl.config.exceptions.EntityAlreadyExistsException;
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

    private boolean isUnique(BovinForm bovinForm) {
        ArrayList<Bull> all = (ArrayList<Bull>) bullRepository.findAll();
        all.addAll(cowRepository.findAll());
        return all.stream()
                .noneMatch(bull -> bull.getLoopNumber().equals(bovinForm.loopNumber()));
    }

    @Override
    public void createBovin(BovinForm bovinForm) {
        if (isUnique(bovinForm)) {
            if (bovinForm.gender()) {
                bullRepository.save(Bull.builder()
                        .coat(bovinForm.coat())
                        .loopNumber(bovinForm.loopNumber())
                        .gender(true)
                        .status(Status.ALIVE)
                        .birthDate(bovinForm.birthDate())
                        .cesarean(bovinForm.cesarean())
                        .mother(findMother(bovinForm))
                        .father(findFather(bovinForm)).build());
            } else {
                cowRepository.save(Cow.builder()
                        .coat(bovinForm.coat())
                        .loopNumber(bovinForm.loopNumber())
                        .gender(false)
                        .status(Status.ALIVE)
                        .birthDate(bovinForm.birthDate())
                        .cesarean(bovinForm.cesarean())
                        .mother(findMother(bovinForm))
                        .father(findFather(bovinForm)).build());
            }
        }else {
            throw new EntityAlreadyExistsException("Bovin avec ce numéro de boucle existe déja");
        }

    }

    private Bull findFather(BovinForm bovinForm) {
        Bull bull = findMother(bovinForm).getPasture().getBovins()
                .stream()
                .filter(bovin -> !(bovin instanceof Cow))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Pas de Taureau sur la pature"));

        return bullRepository.findByLoopNumber(bull.getLoopNumber())
                .orElseThrow(() -> new EntityNotFoundException("Meuh meuh pas trouvé"));
    }

    private Cow findMother(BovinForm bovinForm) {
        return cowRepository.findByLoopNumber(bovinForm.motherLoopNumber())
                .orElseThrow(() -> new EntityNotFoundException("Meuh meuh pas trouvé"));
    }
}
