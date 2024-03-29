package be.technobel.backfermedubeaulieu.dal.datainit;

import be.technobel.backfermedubeaulieu.bll.services.BovinService;
import be.technobel.backfermedubeaulieu.bll.services.PastureService;
import be.technobel.backfermedubeaulieu.pl.models.forms.PastureForm;
import be.technobel.backfermedubeaulieu.pl.models.forms.createBovin.BovinForm;
import be.technobel.backfermedubeaulieu.pl.models.forms.createBovin.ShortBovinForm;
import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
@ConditionalOnProperty(
        value = "datainit",
        havingValue = "true"
)
public class DataInit {

    @Bean
    CommandLineRunner initDatabase(BovinService bovinService, PastureService pastureService) {
        return args -> {
            Faker faker = new Faker(Locale.FRENCH);
            String loopNumber1 = faker.number().digits(4);
            String loopNumber2 = faker.number().digits(4);
            ShortBovinForm bovinFormMale = new ShortBovinForm(
                    loopNumber1,
                    faker.color().name(),
                    true,
                    faker.date().birthdayLocalDate()
            );
            ShortBovinForm bovinFormFemale = new ShortBovinForm(
                    loopNumber2,
                    faker.color().name(),
                    false,
                    faker.date().birthdayLocalDate()
            );
            bovinService.shortCreateBovin(bovinFormMale);
            bovinService.shortCreateBovin(bovinFormFemale);
            PastureForm pastureForm = new PastureForm(
                    faker.funnyName().name(),
                    faker.number().numberBetween(15, 450)
            );
            pastureService.savePasture(pastureForm);
            bovinService.updatePasture(1L, loopNumber1);
            bovinService.updatePasture(1L, loopNumber2);

            for (int i = 0; i < 15; i++) {
                BovinForm bovinFormChild = new BovinForm(
                        faker.number().digits(6),
                        faker.color().name(),
                        faker.bool().bool(),
                        faker.date().birthdayLocalDate(),
                        faker.bool().bool(),
                        bovinFormFemale.loopNumber()
                );
                bovinService.createBovin(bovinFormChild);
            }
        };
    }
}
