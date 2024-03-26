package be.technobel.backfermedubeaulieu.bll.impl;

import be.technobel.backfermedubeaulieu.dal.models.Pasture;
import be.technobel.backfermedubeaulieu.dal.repositories.PastureRepository;
import be.technobel.backfermedubeaulieu.pl.models.forms.PastureForm;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PastureServiceImplTest {

    @Mock
    PastureRepository pastureRepository;

    @InjectMocks
    PastureServiceImpl pastureService;

    @Test
    void getAllPastures() {
        Pasture pasture1 = new Pasture();
        pasture1.setId(1L); // add this line
        pasture1.setName("Pasture1");
        Pasture pasture2 = new Pasture();
        pasture2.setId(2L); // and this line
        pasture2.setName("Pasture2");
        List<Pasture> pastures = Arrays.asList(pasture1, pasture2);

        when(pastureRepository.findAll()).thenReturn(pastures);

        assertThat(pastureService.getAllPastures().size()).isEqualTo(2);
    }

    @Test
    void savePasture() {
        PastureForm pastureForm = new PastureForm("Pasture1", 100.0);
        pastureService.savePasture(pastureForm);

        verify(pastureRepository, times(1)).save(any(Pasture.class));
    }

    @Test
    void findByName_existingPasture() {
        Pasture pasture = new Pasture();
        pasture.setName("Pasture1");

        when(pastureRepository.findByName("Pasture1")).thenReturn(pasture);

        assertThat(pastureService.findByName("Pasture1").getName()).isEqualTo("Pasture1");
    }

    @Test
    void findById_existingPasture() {
        Pasture pasture = new Pasture();
        pasture.setName("Pasture1");
        pasture.setId(1L);

        when(pastureRepository.findById(1L)).thenReturn(java.util.Optional.of(pasture));

        assertThat(pastureService.findById(1L).getId()).isEqualTo(1L);
    }

    @Test
    void findById_nonExistingPasture() {

        when(pastureRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThatThrownBy(() -> pastureService.findById(1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Pature pas trouvée");
    }
}