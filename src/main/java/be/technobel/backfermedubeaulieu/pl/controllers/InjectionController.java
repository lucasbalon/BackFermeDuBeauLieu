package be.technobel.backfermedubeaulieu.pl.controllers;

import be.technobel.backfermedubeaulieu.bll.services.InjectionService;
import be.technobel.backfermedubeaulieu.pl.models.forms.InjectionForm;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/injection")
public class InjectionController {
    private final InjectionService injectionService;

    public InjectionController(InjectionService injectionService) {
        this.injectionService = injectionService;
    }
    @PostMapping()
    public void saveInjection(@RequestBody InjectionForm injection) throws Throwable {
        injectionService.save(injection);
    }

}
