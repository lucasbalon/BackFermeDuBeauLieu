package be.technobel.backfermedubeaulieu.pl.controllers;

import be.technobel.backfermedubeaulieu.bll.services.SubstanceService;
import be.technobel.backfermedubeaulieu.pl.models.dtos.SubstanceDto;
import be.technobel.backfermedubeaulieu.pl.models.forms.SubstanceForm;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/substance")
public class SubstanceController {
    private final SubstanceService substanceService;

    public SubstanceController(SubstanceService substanceService) {
        this.substanceService = substanceService;
    }

    @GetMapping()
    public List<SubstanceDto> getAllSubstances() {
        return substanceService.getAllSubstances();
    }
    @PostMapping()
    public void saveSubstance(@RequestBody SubstanceForm substance) {
        substanceService.saveSubstance(substance);
    }

}
