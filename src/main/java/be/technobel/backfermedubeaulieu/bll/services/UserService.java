package be.technobel.backfermedubeaulieu.bll.services;


import be.technobel.backfermedubeaulieu.pl.models.dtos.AuthDTO;
import be.technobel.backfermedubeaulieu.pl.models.forms.LoginForm;
import be.technobel.backfermedubeaulieu.pl.models.forms.RegisterForm;

public interface UserService {

    void register(RegisterForm form);

    AuthDTO login(LoginForm form);
}
