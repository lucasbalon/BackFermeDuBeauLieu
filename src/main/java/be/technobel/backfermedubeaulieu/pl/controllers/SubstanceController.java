package be.technobel.backfermedubeaulieu.pl.controllers;

import be.technobel.backfermedubeaulieu.bll.services.SubstanceService;
import be.technobel.backfermedubeaulieu.pl.models.dtos.SubstanceDto;
import be.technobel.backfermedubeaulieu.pl.models.forms.SubstanceForm;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/substance")
public class SubstanceController {
    private final SubstanceService substanceService;

    public SubstanceController(SubstanceService substanceService) {
        this.substanceService = substanceService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping()
    public List<SubstanceDto> getAllSubstances() {
        return substanceService.getAllSubstances();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping()
    public void saveSubstance(@Valid @RequestBody SubstanceForm substance) {
        substanceService.saveSubstance(substance);
    }

}
