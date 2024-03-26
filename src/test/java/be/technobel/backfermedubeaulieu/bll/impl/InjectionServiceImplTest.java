package be.technobel.backfermedubeaulieu.bll.impl;

import be.technobel.backfermedubeaulieu.bll.services.BovinService;
import be.technobel.backfermedubeaulieu.bll.services.SubstanceService;
import be.technobel.backfermedubeaulieu.dal.models.Injection;
import be.technobel.backfermedubeaulieu.dal.models.Substance;
import be.technobel.backfermedubeaulieu.dal.models.Bull;
import be.technobel.backfermedubeaulieu.dal.repositories.InjectionRepository;
import be.technobel.backfermedubeaulieu.pl.models.forms.InjectionForm;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InjectionServiceImplTest {

    @Mock
    InjectionRepository injectionRepository;

    @Mock
    SubstanceService substanceService;

    @Mock
    BovinService bovinService;

    @InjectMocks
    InjectionServiceImpl injectionService;

    @Test
    void save_substanceExists_bullExists() {
        InjectionForm injectionForm = new InjectionForm(LocalDate.now(), "1234", "Substance1");
        Substance substance = new Substance();
        substance.setName("Substance1");
        Bull bull = new Bull();
        bull.setLoopNumber("1234");

        when(substanceService.getSubstanceByName(injectionForm.substanceName())).thenReturn(substance);
        when(bovinService.findByLoopNumber(injectionForm.bovinLoopNumber())).thenReturn(bull);

        injectionService.save(injectionForm);

        verify(injectionRepository, times(1)).save(any(Injection.class));
    }

    @Test
    void save_substanceDoesNotExist() {
        InjectionForm injectionForm = new InjectionForm(LocalDate.now(), "1234", "Substance1");

        when(substanceService.getSubstanceByName(injectionForm.substanceName())).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> injectionService.save(injectionForm));

        verify(injectionRepository, never()).save(any(Injection.class));
    }

    @Test
    void save_bullDoesNotExist() {
        InjectionForm injectionForm = new InjectionForm(LocalDate.now(), "1234", "Substance1");
        Substance substance = new Substance();
        substance.setName("Substance1");

        when(substanceService.getSubstanceByName(injectionForm.substanceName())).thenReturn(substance);
        when(bovinService.findByLoopNumber(injectionForm.bovinLoopNumber())).thenThrow(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> injectionService.save(injectionForm));

        verify(injectionRepository, never()).save(any(Injection.class));
    }
}