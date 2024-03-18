package be.technobel.backfermedubeaulieu.dal.datainit;

import be.technobel.backfermedubeaulieu.bll.services.UserService;
import be.technobel.backfermedubeaulieu.pl.config.exceptions.DuplicateUserException;
import be.technobel.backfermedubeaulieu.pl.models.forms.RegisterForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;

@Configuration
@ConditionalOnProperty(
        value = "userinit",
        havingValue = "true",
        matchIfMissing = false
)
public class UserInit {
    @Value("${username}")
    private String username;
    @Value("${password}")
    private String password;

    @Bean
    CommandLineRunner initUser(
            UserService userService
    ) {
        return args -> {
            try {
                userService.register(
                        new RegisterForm(
                                username,
                                password
                        )
                );
            }catch (DuplicateUserException e) {
                System.out.println("UserInit pas nécessaire");
            }
        };
    }
}
