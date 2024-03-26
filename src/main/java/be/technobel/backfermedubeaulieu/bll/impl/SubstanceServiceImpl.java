package be.technobel.backfermedubeaulieu.bll.impl;

import be.technobel.backfermedubeaulieu.bll.services.SubstanceService;
import be.technobel.backfermedubeaulieu.dal.models.Substance;
import be.technobel.backfermedubeaulieu.dal.repositories.SubstanceRepository;
import be.technobel.backfermedubeaulieu.pl.models.dtos.SubstanceDto;
import be.technobel.backfermedubeaulieu.pl.models.forms.SubstanceForm;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubstanceServiceImpl implements SubstanceService {
    private final SubstanceRepository substanceRepository;

    public SubstanceServiceImpl(SubstanceRepository substanceRepository) {
        this.substanceRepository = substanceRepository;
    }

    @Override
    public List<SubstanceDto> getAllSubstances() {
        return substanceRepository.findAll().stream().map(SubstanceDto::fromEntity).toList();
    }

    @Override
    public void saveSubstance(SubstanceForm substance) {
        if (substanceRepository.findByName(substance.name()).isPresent()) {
            throw new EntityNotFoundException("Ce produit existe déjà");
        }
        substanceRepository.save(new Substance(substance.name()));
    }

    @Override
    public Substance getSubstanceByName(String name) {
        // Fetch the Substance from the database
        return substanceRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Ce produit n'existe pas"));
    }
}
