package be.technobel.backfermedubeaulieu.bll.impl;

import be.technobel.backfermedubeaulieu.dal.models.User;
import be.technobel.backfermedubeaulieu.dal.repositories.UserRepository;
import be.technobel.backfermedubeaulieu.pl.config.exceptions.DuplicateUserException;
import be.technobel.backfermedubeaulieu.pl.models.forms.LoginForm;
import be.technobel.backfermedubeaulieu.pl.models.forms.RegisterForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl userService;

    RegisterForm registerForm;
    LoginForm loginForm;

    @BeforeEach
    public void setUp() {
        registerForm = new RegisterForm("test","Test1234=");
        loginForm = new LoginForm("test","Test1234=");
    }

    @Test
    void register_withExistingLogin() {
        when(userRepository.existsByLogin(registerForm.login())).thenReturn(true);

        assertThrows(DuplicateUserException.class, () -> userService.register(registerForm));
    }

    @Test
    void register_withNewUser() {
        when(userRepository.existsByLogin(registerForm.login())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        userService.register(registerForm);

        verify(userRepository).save(any(User.class));
    }

}