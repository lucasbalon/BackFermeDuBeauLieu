package be.technobel.backfermedubeaulieu.pl.controllers;

import be.technobel.backfermedubeaulieu.bll.services.UserService;
import be.technobel.backfermedubeaulieu.pl.models.dtos.AuthDTO;
import be.technobel.backfermedubeaulieu.pl.models.forms.LoginForm;
import be.technobel.backfermedubeaulieu.pl.models.forms.RegisterForm;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDTO> login(@RequestBody @Valid LoginForm form) {
        return ResponseEntity.ok(userService.login(form));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/register")
    public void register(@RequestBody @Valid RegisterForm form) {
        userService.register(form);
    }

}
