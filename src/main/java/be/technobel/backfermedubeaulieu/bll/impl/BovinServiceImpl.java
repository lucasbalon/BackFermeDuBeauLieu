package be.technobel.backfermedubeaulieu.bll.impl;

import be.technobel.backfermedubeaulieu.bll.services.BovinService;
import be.technobel.backfermedubeaulieu.bll.services.PastureService;
import be.technobel.backfermedubeaulieu.dal.models.Bull;
import be.technobel.backfermedubeaulieu.dal.models.Cow;
import be.technobel.backfermedubeaulieu.dal.models.Injection;
import be.technobel.backfermedubeaulieu.dal.models.Pasture;
import be.technobel.backfermedubeaulieu.dal.models.enums.Status;
import be.technobel.backfermedubeaulieu.dal.repositories.BullRepository;
import be.technobel.backfermedubeaulieu.dal.repositories.CowRepository;
import be.technobel.backfermedubeaulieu.dal.repositories.PastureRepository;
import be.technobel.backfermedubeaulieu.pl.config.exceptions.EntityAlreadyExistsException;
import be.technobel.backfermedubeaulieu.pl.models.dtos.BovinDto;
import be.technobel.backfermedubeaulieu.pl.models.dtos.searchBovin.BovinSearchDTO;
import be.technobel.backfermedubeaulieu.pl.models.forms.createBovin.BovinForm;
import be.technobel.backfermedubeaulieu.pl.models.forms.createBovin.IBovinForm;
import be.technobel.backfermedubeaulieu.pl.models.forms.createBovin.ShortBovinForm;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static be.technobel.backfermedubeaulieu.pl.models.dtos.BovinDto.fromEntityInjectionDTO;
import static be.technobel.backfermedubeaulieu.pl.models.dtos.BovinDto.fromEntityScanDTO;

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

    //todo: fonctionne toujours avec l'interface ?
    private boolean isUnique(IBovinForm bovinForm) {
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

    @Override
    public void shortCreateBovin(ShortBovinForm shortBovinForm) {
        if (isUnique(shortBovinForm)) {
            if (shortBovinForm.gender()) {
                bullRepository.save(Bull.builder()
                        .coat(shortBovinForm.coat())
                        .loopNumber(shortBovinForm.loopNumber())
                        .gender(true)
                        .status(Status.ALIVE)
                        .birthDate(shortBovinForm.birthDate())
                        .cesarean(false)
                        .mother(null)
                        .father(null).build());
            } else {
                cowRepository.save(Cow.builder()
                        .coat(shortBovinForm.coat())
                        .loopNumber(shortBovinForm.loopNumber())
                        .gender(false)
                        .status(Status.ALIVE)
                        .birthDate(shortBovinForm.birthDate())
                        .cesarean(false)
                        .mother(null)
                        .father(null).build());
            }
        }else {
            throw new EntityAlreadyExistsException("Bovin avec ce numéro de boucle existe déja");
        }
    }

    @Override
    public String[] findAllBovinsLoopNumber() {
        return bullRepository.findAllBovinsLoopNumber();
    }

    @Override
    public List<BovinSearchDTO> findAllBovins() {
        return bullRepository.findAll().stream().map(BovinSearchDTO::fromEntity).toList();
    }

    @Override
    public BovinDto findBovinById(Long id) {
        Bull bull;
        if (isMale(id)) {
            bull = bullRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Meuh meuh pas trouvé"));
        } else {
            bull = cowRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Meuh meuh pas trouvé"));
        }
        return new BovinDto(
                bull.getLoopNumber(),
                bull.getCoat(),
                bull.isGender(),
                bull.getBirthDate(),
                bull.isCesarean(),
                bull.getStatus(),
                (bull.getFather() != null) ? bull.getFather().getLoopNumber() : "Inconnu",
                (bull.getMother() != null) ? bull.getMother().getLoopNumber() : "Inconnu",
                (bull.getPasture() != null) ? bull.getPasture().getName() : "Pas en pature",
                (bull.getSale() != null) ? bull.getSale().getSaleDate() : LocalDate.EPOCH,
                (bull.getSale() != null) ? bull.getSale().getAmount() : 0,
                (bull.getSale() != null) ? bull.getSale().getCarrierNumber() : 0,
                (bull.getSale() != null) ? bull.getSale().getCustomerNumber() : 0,
                BovinDto.fromEntityBullDTO(bullRepository.findChildren(bull.getId())),
                bull instanceof Cow ? fromEntityScanDTO(((Cow) bull).getScans()) : null,
                fromEntityInjectionDTO(bull.getInjection()));
    }

    @Transactional
    @Override
    public void updatePasture(Long pastureId, Long bovinId) {
        if (isMale(bovinId)) {
            bullRepository.updatePasture(pastureId, bovinId);
        } else {
            cowRepository.updatePasture(pastureId, bovinId);
        }
    }

    @Override
    public List<Bull> findAllBullsByPastureName(String name) {
        return bullRepository.findAllBullsByPastureName(name);
    }


    private boolean isMale(Long id) {
        return bullRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Meuh meuh pas trouvé")).isGender();
    }

    protected Bull findFFather(Pasture pasture) {
        return bullRepository.findFather(pasture);
    }

    protected Bull findFather(BovinForm bovinForm) {
        if (findMother(bovinForm).getPasture() == null) {
            throw new EntityNotFoundException("La vache mère n'a pas de pature attribuée");
        }
        Bull bull;
        try {
            bull = findFFather(findMother(bovinForm).getPasture());
        }catch (Exception e) {
            throw new EntityNotFoundException("Pas ou trop de Taureau sur la pature");
        }

        return bullRepository.findByLoopNumber(bull.getLoopNumber())
                .orElseThrow(() -> new EntityNotFoundException("Meuh meuh pas trouvé"));
    }

    protected Cow findMother(BovinForm bovinForm) {
        try {
            return cowRepository.findByLoopNumber(bovinForm.motherLoopNumber())
                    .orElseThrow(() -> new EntityNotFoundException("Meuh meuh pas trouvé"));
        }catch (ClassCastException exception) {
            throw new EntityNotFoundException("La mère n'est pas une vache");
        }

    }
}
