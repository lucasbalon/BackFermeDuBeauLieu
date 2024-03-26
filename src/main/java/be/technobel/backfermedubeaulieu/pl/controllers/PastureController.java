package be.technobel.backfermedubeaulieu.pl.controllers;

import be.technobel.backfermedubeaulieu.bll.services.PastureService;
import be.technobel.backfermedubeaulieu.pl.models.dtos.PastureDto;
import be.technobel.backfermedubeaulieu.pl.models.forms.PastureForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pasture")
public class PastureController {

    private final PastureService pastureService;

    public PastureController(PastureService pastureService) {
        this.pastureService = pastureService;
    }

    @PostMapping
    public ResponseEntity<Void> createPasture(@RequestBody PastureForm pastureForm) {
        pastureService.savePasture(pastureForm);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping
    public ResponseEntity<List<PastureDto>> findAllPastures() {
        return ResponseEntity.ok(pastureService.getAllPastures());
    }
}
