package be.technobel.backfermedubeaulieu.bll.impl;

import be.technobel.backfermedubeaulieu.dal.models.Cow;
import be.technobel.backfermedubeaulieu.dal.models.Scan;
import be.technobel.backfermedubeaulieu.dal.repositories.CowRepository;
import be.technobel.backfermedubeaulieu.dal.repositories.ScanRepository;
import be.technobel.backfermedubeaulieu.pl.models.forms.ScanForm;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScanServiceImplTest {

    @Mock
    ScanRepository scanRepository;

    @Mock
    CowRepository cowRepository;

    @InjectMocks
    ScanServiceImpl scanService;


    @Test
    void create_validCow_loopNumber() {
        ScanForm scanForm = new ScanForm(LocalDate.now(), true, "1234");
        Cow cow = new Cow();
        cow.setLoopNumber("1234");

        when(cowRepository.findByLoopNumber(scanForm.loopNumber())).thenReturn(Optional.of(cow));

        scanService.create(scanForm);

        verify(scanRepository, times(1)).save(any(Scan.class));
    }

    @Test
    void create_invalidCow_loopNumber() {
        ScanForm scanForm = new ScanForm(LocalDate.now(), true, "1234");

        when(cowRepository.findByLoopNumber(scanForm.loopNumber())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> scanService.create(scanForm));
        verify(scanRepository, times(0)).save(any(Scan.class));
    }

}