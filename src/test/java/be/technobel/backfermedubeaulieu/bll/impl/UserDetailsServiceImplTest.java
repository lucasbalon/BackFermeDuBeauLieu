package be.technobel.backfermedubeaulieu.bll.impl;

import be.technobel.backfermedubeaulieu.dal.models.User;
import be.technobel.backfermedubeaulieu.dal.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    private final String nonExistingUserLogin = "nonExistingUser";
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    @Test
    void loadUserByUsername_existingUser() {
        User user = new User();
        String existingUserLogin = "existingUser";
        user.setLogin(existingUserLogin);
        when(userRepository.findByLogin(existingUserLogin)).thenReturn(Optional.of(user));

        assertNotNull(userDetailsService.loadUserByUsername(existingUserLogin));
    }

    @Test
    void loadUserByUsername_nonExistingUser() {
        when(userRepository.findByLogin(nonExistingUserLogin)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(nonExistingUserLogin));
    }
}