package be.technobel.backfermedubeaulieu.bll.impl;

import be.technobel.backfermedubeaulieu.dal.models.Substance;
import be.technobel.backfermedubeaulieu.dal.repositories.SubstanceRepository;
import be.technobel.backfermedubeaulieu.pl.models.forms.SubstanceForm;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubstanceServiceImplTest {

    @Mock
    SubstanceRepository substanceRepository;

    @InjectMocks
    SubstanceServiceImpl substanceService;

    @Test
    void getAllSubstances() {
        List<Substance> substances = new ArrayList<>();
        substances.add(new Substance("Water"));
        substances.add(new Substance("Air"));
        substances.add(new Substance("Earth"));

        when(substanceRepository.findAll()).thenReturn(substances);

        assertThat(substanceService.getAllSubstances().size()).isEqualTo(3);
    }

    @Test
    void getAllSubstances_empty() {
        when(substanceRepository.findAll()).thenReturn(new ArrayList<>());

        assertThat(substanceService.getAllSubstances().size()).isEqualTo(0);
    }

    @Test
    void saveSubstance_newSubstance() {
        SubstanceForm substanceForm = new SubstanceForm("Water");
        when(substanceRepository.findByName(substanceForm.name())).thenReturn(Optional.empty());
        substanceService.saveSubstance(substanceForm);

        verify(substanceRepository, times(1)).save(any());
    }

    @Test
    void saveSubstance_existingSubstance() {
        SubstanceForm substanceForm = new SubstanceForm("Water");
        when(substanceRepository.findByName(substanceForm.name())).thenReturn(Optional.of(new Substance("Water")));

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> substanceService.saveSubstance(substanceForm))
                .withMessage("Ce produit existe déjà");

        verify(substanceRepository, times(0)).save(any());
    }

    @Test
    void getSubstanceByName_existingSubstance() {
        when(substanceRepository.findByName("Water")).thenReturn(Optional.of(new Substance("Water")));

        Substance substance = substanceService.getSubstanceByName("Water");

        assertThat(substance.getName()).isEqualTo("Water");
    }

    @Test
    void getSubstanceByName_nonExistingSubstance() {
        when(substanceRepository.findByName("Water")).thenReturn(Optional.empty());

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> substanceService.getSubstanceByName("Water"))
                .withMessage("Ce produit n'existe pas");
    }

    @Test
    void getSubstanceByName_nullName() {
        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> substanceService.getSubstanceByName(null))
                .withMessage("Ce produit n'existe pas");
    }
}