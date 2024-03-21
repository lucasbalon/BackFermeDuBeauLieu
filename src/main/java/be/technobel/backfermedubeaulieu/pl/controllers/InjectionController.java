package be.technobel.backfermedubeaulieu.pl.controllers;

import be.technobel.backfermedubeaulieu.bll.services.InjectionService;
import be.technobel.backfermedubeaulieu.pl.models.forms.InjectionForm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/injectionn")
public class InjectionController {
    private final InjectionService injectionService;

    public InjectionController(InjectionService injectionService) {
        this.injectionService = injectionService;
    }
    @PostMapping("/")
    public void saveInjection(@RequestBody InjectionForm injection) throws Throwable {
        injectionService.save(injection);
    }

}
