package be.technobel.backfermedubeaulieu.bll.impl;

import be.technobel.backfermedubeaulieu.bll.services.UserService;
import be.technobel.backfermedubeaulieu.dal.models.User;
import be.technobel.backfermedubeaulieu.dal.repositories.UserRepository;
import be.technobel.backfermedubeaulieu.pl.config.exceptions.DuplicateUserException;
import be.technobel.backfermedubeaulieu.pl.config.security.JWTProvider;
import be.technobel.backfermedubeaulieu.pl.models.dtos.AuthDTO;
import be.technobel.backfermedubeaulieu.pl.models.forms.LoginForm;
import be.technobel.backfermedubeaulieu.pl.models.forms.RegisterForm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JWTProvider jwtProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(RegisterForm form) {
        if (form == null)
            throw new IllegalArgumentException("form peut pas être null");
        if (userRepository.existsByLogin(form.login()))
            throw new DuplicateUserException("Login already used");
        User entity = new User();
        entity.setLogin(form.login());
        entity.setPassword(passwordEncoder.encode(form.password()));

        userRepository.save(entity);
    }

    @Override
    public AuthDTO login(LoginForm form) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(form.login(), form.password()));

        Optional<User> optionalUser = userRepository.findByLogin(form.login());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            String token = jwtProvider.generateToken(user.getUsername());

            return AuthDTO.builder()
                    .token(token)
                    .login(user.getLogin())
                    .build();
        } else {
            throw new UsernameNotFoundException("Username not found");
        }
    }
}
