package be.technobel.backfermedubeaulieu.bll.impl;

import be.technobel.backfermedubeaulieu.bll.services.BovinService;
import be.technobel.backfermedubeaulieu.bll.services.PastureService;
import be.technobel.backfermedubeaulieu.dal.models.Bull;
import be.technobel.backfermedubeaulieu.dal.models.Cow;
import be.technobel.backfermedubeaulieu.dal.models.Pasture;
import be.technobel.backfermedubeaulieu.dal.repositories.BovinRepository;
import be.technobel.backfermedubeaulieu.dal.repositories.BullRepository;
import be.technobel.backfermedubeaulieu.dal.repositories.CowRepository;
import be.technobel.backfermedubeaulieu.pl.config.exceptions.EntityAlreadyExistsException;
import be.technobel.backfermedubeaulieu.pl.models.dtos.BovinDto;
import be.technobel.backfermedubeaulieu.pl.models.dtos.searchBovin.BovinSearchDTO;
import be.technobel.backfermedubeaulieu.pl.models.forms.createBovin.BovinForm;
import be.technobel.backfermedubeaulieu.pl.models.forms.createBovin.IBovinForm;
import be.technobel.backfermedubeaulieu.pl.models.forms.createBovin.ShortBovinForm;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static be.technobel.backfermedubeaulieu.pl.models.dtos.searchBovin.BovinSearchDTO.fromEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BovinServiceImplTest {

    // les mock nécessaires pour le service
    @Mock
    private BullRepository bullRepository;
    @Mock
    private CowRepository cowRepository;
    @Mock
    private PastureService pastureService;
    @Mock
    private BovinRepository bovinRepository;

    // le service à tester
    @InjectMocks
    private BovinServiceImpl bovinService;

    // les données de test
    private String loopNumber = "1234";
    private Bull bull;
    private Cow cow;
    private BovinSearchDTO bullDto;
    private BovinSearchDTO cowDto;

    @BeforeEach
    void setUp() {
        // Initialisation des données de test
        bull = new Bull();
        bull.setId(1L);
        bull.setLoopNumber(loopNumber);

        cow = new Cow();
        cow.setId(2L);
        cow.setLoopNumber(loopNumber);

        bullDto = fromEntity(bull);
        cowDto = fromEntity(cow);
    }

    @Test
    void findBovinsByLoopNumber_existingEntries() {
        Bull bull = new Bull();
        bull.setId(1L);
        bull.setLoopNumber("1234");
        Cow cow = new Cow();
        cow.setId(2L);
        cow.setLoopNumber("1234");

        when(bullRepository.findAllByLoopNumber("1234")).thenReturn(Collections.singletonList(bull));
        when(cowRepository.findAllByLoopNumber("1234")).thenReturn(Collections.singletonList(cow));

        List<BovinSearchDTO> result = bovinService.findBovinsByLoopNumber("1234");

        verify(bullRepository, times(1)).findAllByLoopNumber("1234");
        verify(cowRepository, times(1)).findAllByLoopNumber("1234");

        assertThat(result).hasSize(2);
    }

    @Test
    void findBovinsByLoopNumber_noMatches() {
        when(bullRepository.findAllByLoopNumber("nonexistent")).thenReturn(Collections.emptyList());
        when(cowRepository.findAllByLoopNumber("nonexistent")).thenReturn(Collections.emptyList());

        List<BovinSearchDTO> result = bovinService.findBovinsByLoopNumber("nonexistent");

        verify(bullRepository, times(1)).findAllByLoopNumber("nonexistent");
        verify(cowRepository, times(1)).findAllByLoopNumber("nonexistent");

        assertThat(result).isEmpty();
    }

    @Test
    void findBovinsByLoopNumber_validLoopNumber_MultipleBovins() {
        String loopNumber = "123";

        // Mocking bulls
        Bull bull1 = new Bull();
        bull1.setId(1L);
        Bull bull2 = new Bull();
        bull1.setId(2L);
        when(bullRepository.findAllByLoopNumber(loopNumber)).thenReturn(Arrays.asList(bull1, bull2));

        // Mocking cows
        Cow cow1 = new Cow();
        cow1.setId(3L);
        Cow cow2 = new Cow();
        cow1.setId(4L);
        when(cowRepository.findAllByLoopNumber(loopNumber)).thenReturn(Arrays.asList(cow1, cow2));

        // Testing
        List<BovinSearchDTO> result = bovinService.findBovinsByLoopNumber(loopNumber);

        assertThat(result).hasSize(4);
    }
    @Test
    void findBovinsByLoopNumber_validLoopNumber_BovinsAsBulls() {
        String loopNumber = "123";

        // Mocking bulls
        Bull bull1 = new Bull();
        bull1.setId(1L);
        Bull bull2 = new Bull();
        bull1.setId(2L);
        when(bullRepository.findAllByLoopNumber(loopNumber)).thenReturn(Arrays.asList(bull1, bull2));

        // Mocking cows
        when(cowRepository.findAllByLoopNumber(loopNumber)).thenReturn(Collections.emptyList());

        // Testing
        List<BovinSearchDTO> result = bovinService.findBovinsByLoopNumber(loopNumber);

        assertThat(result).hasSize(2);
    }
    @Test
    void findBovinsByLoopNumber_validLoopNumber_BovinsAsCows() {
        String loopNumber = "123";

        // Mocking bulls
        when(bullRepository.findAllByLoopNumber(loopNumber)).thenReturn(Collections.emptyList());

        // Mocking cows
        Cow cow1 = new Cow();
        cow1.setId(1L);
        Cow cow2 = new Cow();
        cow1.setId(2L);
        when(cowRepository.findAllByLoopNumber(loopNumber)).thenReturn(Arrays.asList(cow1, cow2));

        // Testing
        List<BovinSearchDTO> result = bovinService.findBovinsByLoopNumber(loopNumber);

        assertThat(result).hasSize(2);
    }

    @Test
    void findBovinsByLoopNumber_validLoopNumber_NoBovins() {
        String loopNumber = "123";

        // Mocking bulls
        when(bullRepository.findAllByLoopNumber(loopNumber)).thenReturn(Collections.emptyList());

        // Mocking cows
        when(cowRepository.findAllByLoopNumber(loopNumber)).thenReturn(Collections.emptyList());

        // Testing
        List<BovinSearchDTO> result = bovinService.findBovinsByLoopNumber(loopNumber);

        assertThat(result).isEmpty();
    }
    @Test
    void isUnique_LoopNumberExistsInBull_ReturnsFalse() {
        IBovinForm bovinForm = mock(IBovinForm.class);
        when(bovinForm.loopNumber()).thenReturn("123");

        Bull bull1 = new Bull();
        bull1.setLoopNumber("123");
        when(bullRepository.findAll()).thenReturn(Arrays.asList(bull1));
        when(cowRepository.findAll()).thenReturn(Collections.emptyList());

        assertFalse(bovinService.isUnique(bovinForm));
    }

    @Test
    void testFindBovinsByLoopNumber() {
        // Définir le comportement des mocks
        when(bullRepository.findAllByLoopNumber(loopNumber)).thenReturn(Collections.singletonList(bull));
        when(cowRepository.findAllByLoopNumber(loopNumber)).thenReturn(Collections.singletonList(cow));

        // Appeler la méthode à tester
        List<BovinSearchDTO> result = bovinService.findBovinsByLoopNumber(loopNumber);

        // Vérifier le résultat
        assertNotNull(result, "Le résultat ne devrait pas être null");
        assertEquals(2, result.size(), "Le résultat devrait contenir deux éléments");
        assertTrue(result.contains(bullDto), "Le résultat devrait contenir le dto bull");
        assertTrue(result.contains(cowDto), "Le résultat devrait contenir le dto cow");

        // Vérifier l'interaction avec les mocks
        verify(bullRepository, times(1)).findAllByLoopNumber(loopNumber);
        verify(cowRepository, times(1)).findAllByLoopNumber(loopNumber);
    }

    @Test
    void testIsUnique_bullLoopNumberIsNotUnique() {
        // Define test data
        String notUniqueLoopNumber = "1234";
        IBovinForm bovinForm = mock(IBovinForm.class);
        when(bovinForm.loopNumber()).thenReturn(notUniqueLoopNumber);
        Bull existingBullWithSameLoopNumber = new Bull();
        existingBullWithSameLoopNumber.setLoopNumber(notUniqueLoopNumber);

        // Define the behavior of mock
        when(bullRepository.findAll()).thenReturn(Collections.singletonList(existingBullWithSameLoopNumber));
        when(cowRepository.findAll()).thenReturn(Collections.emptyList());

        // Call the method to test
        boolean result = bovinService.isUnique(bovinForm);

        // Verify the result
        assertFalse(result, "The result should be false when there is an existing bull with same loop number");

        // Verify the interactions with the mock
        verify(bullRepository, times(1)).findAll();
        verify(cowRepository, times(1)).findAll();
    }


    @Test
    void testCreateBovin_notUnique() {
        // Test data
        BovinForm bovinForm = new BovinForm( "1234", "red", false, LocalDate.now(), true, "1235");
        Bull existingBullWithSameLoopNumber = new Bull();
        existingBullWithSameLoopNumber.setLoopNumber("1234");

        // If not unique, throw EntityAlreadyExistsException
        when(bullRepository.findAll()).thenReturn(Collections.singletonList(existingBullWithSameLoopNumber));

        assertThrows(EntityAlreadyExistsException.class, () -> bovinService.createBovin(bovinForm));
    }
    @Test
    void testShortCreateBovin_newBull() {
        // Test data
        ShortBovinForm shortBovinForm = new ShortBovinForm("5678", "red", true, LocalDate.now());

        // If the form is unique, save should be called
        when(bullRepository.findAll()).thenReturn(Collections.emptyList());
        when(cowRepository.findAll()).thenReturn(Collections.emptyList());

        bovinService.shortCreateBovin(shortBovinForm);

        verify(bullRepository, times(1)).save(any(Bull.class));
    }

    @Test
    void testShortCreateBovin_newCow() {
        // Test data
        ShortBovinForm shortBovinForm = new ShortBovinForm("5678", "red", false, LocalDate.now());

        // If the form is unique, save should be called
        when(bullRepository.findAll()).thenReturn(Collections.emptyList());
        when(cowRepository.findAll()).thenReturn(Collections.emptyList());

        bovinService.shortCreateBovin(shortBovinForm);

        verify(cowRepository, times(1)).save(any(Cow.class));
    }

    @Test
    void testShortCreateBovin_notUnique() {
        // Test data
        ShortBovinForm shortBovinForm = new ShortBovinForm("1234", "red", true, LocalDate.now());
        Bull existingBullWithSameLoopNumber = new Bull();
        existingBullWithSameLoopNumber.setLoopNumber("1234");

        // If not unique, throw EntityAlreadyExistsException
        when(bullRepository.findAll()).thenReturn(Collections.singletonList(existingBullWithSameLoopNumber));

        assertThrows(EntityAlreadyExistsException.class, () -> bovinService.shortCreateBovin(shortBovinForm));
    }

    @Test
    void testFindAllCowLoopNumber() {
        // Given
        String[] cowLoopNumbers = new String[] {"3333", "4444"};

        when(cowRepository.findAllBovinsLoopNumber()).thenReturn(cowLoopNumbers);

        // When
        String[] returnedCowLoopNumbers = bovinService.findAllCowLoopNumber();

        // Then
        assertArrayEquals(cowLoopNumbers, returnedCowLoopNumbers);

        verify(cowRepository, times(1)).findAllBovinsLoopNumber();
    }
    @Test
    void testFindAllCowLoopNumber_NoLoopNumbers() {
        // Given
        when(cowRepository.findAllBovinsLoopNumber()).thenReturn(new String[0]);

        // When
        String[] returnedCowLoopNumbers = bovinService.findAllCowLoopNumber();

        // Then
        assertEquals(0, returnedCowLoopNumbers.length);
        verify(cowRepository, times(1)).findAllBovinsLoopNumber();
    }

    @Test
    void testFindAllCowLoopNumber_NullLoopNumbers() {
        // Given
        when(cowRepository.findAllBovinsLoopNumber()).thenReturn(null);

        // When
        String[] returnedCowLoopNumbers = bovinService.findAllCowLoopNumber();

        // Then
        assertNull(returnedCowLoopNumbers);
        verify(cowRepository, times(1)).findAllBovinsLoopNumber();
    }

    @Test
    void testFindAllBullLoopNumber() {
        // Given
        String[] bullLoopNumbers = new String[] {"1111", "2222"};

        when(bullRepository.findAllBovinsLoopNumber()).thenReturn(bullLoopNumbers);

        // When
        String[] returnedBullLoopNumbers = bovinService.findAllBullLoopNumber();

        // Then
        assertArrayEquals(bullLoopNumbers, returnedBullLoopNumbers);

        verify(bullRepository, times(1)).findAllBovinsLoopNumber();
    }
    @Test
    void testFindAllBullLoopNumber_NoLoopNumbers() {
        // Given
        when(bullRepository.findAllBovinsLoopNumber()).thenReturn(new String[0]);

        // When
        String[] returnedBullLoopNumbers = bovinService.findAllBullLoopNumber();

        // Then
        assertEquals(0, returnedBullLoopNumbers.length);
        verify(bullRepository, times(1)).findAllBovinsLoopNumber();
    }

    @Test
    void testFindAllBullLoopNumber_NullLoopNumbers() {
        // Given
        when(bullRepository.findAllBovinsLoopNumber()).thenReturn(null);

        // When
        String[] returnedBullLoopNumbers = bovinService.findAllBullLoopNumber();

        // Then
        assertNull(returnedBullLoopNumbers);
        verify(bullRepository, times(1)).findAllBovinsLoopNumber();
    }

    @Test
    void testFindBullLoopnumberByPastureId_LoopNumberExists() {
        // Given
        long id = 1L;
        String expectedLoopNumber = "1234";
        when(bullRepository.findBullLoopNumberByPastureId(id)).thenReturn(expectedLoopNumber);

        // When
        String returnedLoopNumber = bovinService.findBullLoopnumberByPastureId(id);

        // Then
        assertEquals(expectedLoopNumber, returnedLoopNumber);
        verify(bullRepository, times(1)).findBullLoopNumberByPastureId(id);
    }

    @Test
    void testFindBullLoopnumberByPastureId_NoLoopNumber() {
        // Given
        long id = 1L;
        when(bullRepository.findBullLoopNumberByPastureId(id)).thenReturn(null);

        // When
        String returnedMessage = bovinService.findBullLoopnumberByPastureId(id);

        // Then
        assertEquals("Pas de Taureau assigné à cette pature", returnedMessage);
        verify(bullRepository, times(1)).findBullLoopNumberByPastureId(id);
    }

    @Test
    void testFindAllBovins() {
        // Given
        Bull bull1 = new Bull();
        Bull bull2 = new Bull();
        when(bullRepository.findAll()).thenReturn(List.of(bull1, bull2));

        // When
        List<BovinSearchDTO> returnedList = bovinService.findAllBovins();

        // Then
        assertEquals(2, returnedList.size());
        assertEquals(BovinSearchDTO.fromEntity(bull1), returnedList.get(0));
        assertEquals(BovinSearchDTO.fromEntity(bull2), returnedList.get(1));
        verify(bullRepository, times(1)).findAll();
    }
    @Test
    void testFindAllBovins_NoBovins() {
        // Given
        when(bullRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<BovinSearchDTO> returnedList = bovinService.findAllBovins();

        // Then
        assertTrue(returnedList.isEmpty());
        verify(bullRepository, times(1)).findAll();
    }


    @Test
    void testFindBovinById_NoBovin() {
        // Given
        Long id = 1L;
        when(bullRepository.findById(id)).thenReturn(Optional.empty());

        // When
        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            bovinService.findBovinById(id);
        });

        // Then
        assertEquals("Meuh meuh pas trouvé", exception.getMessage());
        verify(bullRepository, times(1)).findById(id);
    }


}