package be.technobel.backfermedubeaulieu.bll.impl;

import be.technobel.backfermedubeaulieu.bll.services.BovinService;
import be.technobel.backfermedubeaulieu.dal.models.Bull;
import be.technobel.backfermedubeaulieu.dal.models.enums.Status;
import be.technobel.backfermedubeaulieu.dal.repositories.SaleRepository;
import be.technobel.backfermedubeaulieu.pl.config.exceptions.AlreadyDeadException;
import be.technobel.backfermedubeaulieu.pl.models.forms.SaleForm;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SaleServiceImplTest {

    @Mock
    SaleRepository saleRepository;

    @Mock
    BovinService bovinService;

    @InjectMocks
    SaleServiceImpl saleService;


    @Test
    void createSale_bullAlive() {
        SaleForm saleForm = new SaleForm(LocalDate.now(), 100,1234, 456, "6789");
        Bull bull = new Bull();
        bull.setStatus(Status.ALIVE);

        when(bovinService.findByLoopNumber(saleForm.bovinLoopNumber())).thenReturn(bull);

        saleService.createSale(saleForm);

        verify(saleRepository, times(1)).save(any());
        verify(bovinService, times(1)).setStatus(Status.SOLD, saleForm.bovinLoopNumber());
    }

    @Test
    void createSale_bullSold() {
        SaleForm saleForm = new SaleForm(LocalDate.now(), 100,1234, 456, "6789");
        Bull bull = new Bull();
        bull.setStatus(Status.SOLD);

        when(bovinService.findByLoopNumber(saleForm.bovinLoopNumber())).thenReturn(bull);

        assertThrows(AlreadyDeadException.class, () -> saleService.createSale(saleForm));
        verify(saleRepository, times(0)).save(any());
    }

    @Test
    void createSale_bullDead() {
        SaleForm saleForm = new SaleForm(LocalDate.now(), 100,1234, 456, "6789");
        Bull bull = new Bull();
        bull.setStatus(Status.DEAD);

        when(bovinService.findByLoopNumber(saleForm.bovinLoopNumber())).thenReturn(bull);

        assertThrows(AlreadyDeadException.class, () -> saleService.createSale(saleForm));
        verify(saleRepository, times(0)).save(any());
    }

    @Test
    void createSale_bullNotFound() {
        SaleForm saleForm = new SaleForm(LocalDate.now(), 100,1234, 456, "6789");

        when(bovinService.findByLoopNumber(saleForm.bovinLoopNumber())).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> saleService.createSale(saleForm));
        verify(saleRepository, times(0)).save(any());
    }
}