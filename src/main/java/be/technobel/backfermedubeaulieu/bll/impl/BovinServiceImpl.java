package be.technobel.backfermedubeaulieu.bll.impl;

import be.technobel.backfermedubeaulieu.bll.services.BovinService;
import be.technobel.backfermedubeaulieu.bll.services.PastureService;
import be.technobel.backfermedubeaulieu.dal.models.Bull;
import be.technobel.backfermedubeaulieu.dal.models.Cow;
import be.technobel.backfermedubeaulieu.dal.models.Injection;
import be.technobel.backfermedubeaulieu.dal.models.Pasture;
import be.technobel.backfermedubeaulieu.dal.models.enums.Status;
import be.technobel.backfermedubeaulieu.dal.repositories.BovinRepository;
import be.technobel.backfermedubeaulieu.dal.repositories.BullRepository;
import be.technobel.backfermedubeaulieu.dal.repositories.CowRepository;
import be.technobel.backfermedubeaulieu.dal.repositories.PastureRepository;
import be.technobel.backfermedubeaulieu.pl.config.exceptions.ConsanguinityException;
import be.technobel.backfermedubeaulieu.pl.config.exceptions.EntityAlreadyExistsException;
import be.technobel.backfermedubeaulieu.pl.models.dtos.BovinDto;
import be.technobel.backfermedubeaulieu.pl.models.dtos.BovinShortDTO;
import be.technobel.backfermedubeaulieu.pl.models.dtos.PastureFullDTO;
import be.technobel.backfermedubeaulieu.pl.models.dtos.searchBovin.BovinSearchDTO;
import be.technobel.backfermedubeaulieu.pl.models.forms.createBovin.BovinForm;
import be.technobel.backfermedubeaulieu.pl.models.forms.createBovin.IBovinForm;
import be.technobel.backfermedubeaulieu.pl.models.forms.createBovin.ShortBovinForm;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static be.technobel.backfermedubeaulieu.pl.models.dtos.BovinDto.fromEntityInjectionDTO;
import static be.technobel.backfermedubeaulieu.pl.models.dtos.BovinDto.fromEntityScanDTO;

@Service
public class BovinServiceImpl implements BovinService {
    private final BullRepository bullRepository;
    private final CowRepository cowRepository;
    private final PastureService pastureService;

    public BovinServiceImpl(BullRepository bullRepository, CowRepository cowRepository, PastureService pastureService) {
        this.bullRepository = bullRepository;
        this.cowRepository = cowRepository;
        this.pastureService = pastureService;
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
        return Stream.concat(Arrays.stream(findAllBullLoopNumber()), Arrays.stream(findAllCowLoopNumber()))
                .toArray(String[]::new);
    }

    @Override
    public String[] findAllCowLoopNumber() {
        return cowRepository.findAllBovinsLoopNumber();
    }

    @Override
    public String[] findAllBullLoopNumber() {
        return bullRepository.findAllBovinsLoopNumber();
    }

    @Override
    public String findBullLoopnumberByPastureId(long id) {
        String loopNumber = bullRepository.findBullLoopNumberByPastureId(id);
        return Objects.requireNonNullElse(loopNumber, "Pas de Taureau assigné à cette pature");
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
    public void updatePasture(Long pastureId, String cowLoopnumber) {
        String loopNumber = findBullLoopnumberByPastureId(pastureId);
        Long bovinId;
        try {
            bovinId = cowRepository.findByLoopNumber(cowLoopnumber).orElseThrow(() -> new EntityNotFoundException("Vache n'existe pas")).getId();
        }catch (Exception e) {
            bovinId = bullRepository.findByLoopNumber(cowLoopnumber).orElseThrow(() -> new EntityNotFoundException("Vache n'existe pas")).getId();
        }

        if (loopNumber.equals("Pas de Taureau assigné à cette pature")) {
            bullRepository.updatePasture(pastureId, cowLoopnumber);
        } else {
            Bull bull = bullRepository.findByLoopNumber(findBullLoopnumberByPastureId(pastureId)).orElseThrow(() -> new EntityNotFoundException("Taureau n'existe pas"));
            Cow cow = cowRepository.findById(bovinId).orElseThrow(() -> new EntityNotFoundException("Vache n'existe pas"));
            if (!isConsanguinity(bull, cowLoopnumber)) {
                if (isMale(bovinId)) {
                    bullRepository.updatePasture(pastureId, cowLoopnumber);
                } else {
                    cowRepository.updatePasture(pastureId, cowLoopnumber);
                }
            } else {
                throw new ConsanguinityException("Alerte consanguinité !");
            }
        }
    }

    @Transactional
    @Override
    public void updatePastureBull(Long pastureId, String bullLoopnumber) {
        PastureFullDTO pastureFullDTO = findPasture(pastureId);
        Bull bull = bullRepository.findByLoopNumber(bullLoopnumber).orElseThrow(() -> new EntityNotFoundException("Taureau introuvable, vérifier le numéro"));
        List<String> pastureCows = pastureFullDTO.pastureCows().stream().map(BovinShortDTO::loopNumber).toList();
        for (String cowLoopnumber : pastureCows) {
            if (isConsanguinity(bull, cowLoopnumber)) {
                throw new ConsanguinityException("Alerte consanguinité !");
            }
        }
        if (!bullRepository.findAllBullsByPastureName(pastureService.findById(pastureId).getName()).isEmpty()) {
            bullRepository.deleteAllByPasture(pastureId);
        }
        bullRepository.updatePasture(pastureId, bullLoopnumber);
    }

    @Override
    public PastureFullDTO findPasture(long id) {
        Bull bull = bullRepository.findBullByPastureId(id).orElse(new Bull());
        Pasture pasture = pastureService.findById(id);
        return new PastureFullDTO(
                pasture.getName(),
                bull.getLoopNumber(),
                cowRepository.findAvalaibleCowsByPasture(pasture.getId()).stream().map(BovinShortDTO::fromEntity).toList(),
                cowRepository.findCowInPasture(pasture.getId()).stream().map(BovinShortDTO::fromEntity).toList()
        );
    }

    @Override
    public List<BovinShortDTO> findAvailableBull() {
        return bullRepository.findAvailableBull().stream().map(BovinShortDTO::fromEntity).toList();
    }

    @Transactional
    @Override
    public void removeCowFromPasture(String loopNumber) {
        cowRepository.removeFromPasture(loopNumber);
    }

    @Override
    public Bull findByLoopNumber(String loopNumber) {
        if (isMale(bullRepository.findByLoopNumber(loopNumber).orElseThrow(() -> new EntityNotFoundException("Meuh meuh pas trouvé")).getId())) {
            return bullRepository.findByLoopNumber(loopNumber).orElseThrow(() -> new EntityNotFoundException("Meuh meuh pas trouvé"));
        } else {
            return cowRepository.findByLoopNumber(loopNumber).orElseThrow(() -> new EntityNotFoundException("Meuh meuh pas trouvé"));
        }
    }

    private boolean isConsanguinity(Bull bull, String loopNumber) {
        // Condition de fin de récursivité :
        // Soit on a trouvé un match
        // Soit il n'y a pas de parents à contrôler (fin de l'arbre généalogique)
        if (bull == null) {
            return false;

        }
        if (cowRepository.findByLoopNumber(loopNumber).get().getFather() != null) {
            if (bull.getLoopNumber().equals(cowRepository.findByLoopNumber(loopNumber).get().getFather().getLoopNumber())) {
                return true;
            }
        }
        if (cowRepository.findByLoopNumber(loopNumber).get().getMother() != null) {
            if (bull.getLoopNumber().equals(cowRepository.findByLoopNumber(loopNumber).get().getMother().getLoopNumber())) {
                return true;
            }
        }
        // Recherche récursive dans l'arbre généalogique
        return isConsanguinity(bull.getFather(), loopNumber) ||
                isConsanguinity(bull.getMother(), loopNumber);
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
