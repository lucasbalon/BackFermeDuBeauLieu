package be.technobel.backfermedubeaulieu.pl.controllers;

import be.technobel.backfermedubeaulieu.bll.services.BovinService;
import be.technobel.backfermedubeaulieu.pl.models.dtos.BovinDto;
import be.technobel.backfermedubeaulieu.pl.models.dtos.searchBovin.BovinSearchDTO;
import be.technobel.backfermedubeaulieu.pl.models.forms.createBovin.BovinForm;
import be.technobel.backfermedubeaulieu.pl.models.forms.createBovin.ShortBovinForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/bovin")
public class BovinController {
    private final BovinService bovinService;

    public BovinController(BovinService bovinService) {
        this.bovinService = bovinService;
    }

    @GetMapping("/{loopNumber}")
    public ResponseEntity<List<BovinSearchDTO>> findBovinsByLoopNumber(@PathVariable String loopNumber) {
        return ResponseEntity.ok(bovinService.findBovinsByLoopNumber(loopNumber));
    }

    @PostMapping
    public ResponseEntity<Void> createBovin(@RequestBody BovinForm bovinForm) {
        bovinService.createBovin(bovinForm);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PostMapping("/short")
    public ResponseEntity<Void> shortCreateBovin(@RequestBody ShortBovinForm shortBovinForm) {
        bovinService.shortCreateBovin(shortBovinForm);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/loopNumbers")
    public ResponseEntity<String[]> findAllBovinsLoopNumber() {
        return ResponseEntity.ok(bovinService.findAllBovinsLoopNumber());
    }
    @GetMapping("/loopNumbers/cow")
    public ResponseEntity<String[]> findAllCowsLoopNumber() {
        return ResponseEntity.ok(bovinService.findAllCowLoopNumber());
    }
    @GetMapping("/loopNumbers/bull")
    public ResponseEntity<String[]> findAllBullsLoopNumber() {
        return ResponseEntity.ok(bovinService.findAllBullLoopNumber());
    }
    @GetMapping("/bull/pasture/{id}")
    public ResponseEntity<String> findAllBullsByPastureName(@PathVariable long id) {
        return ResponseEntity.ok(bovinService.findBullByPastureId(id));
    }
    @GetMapping("/pasture/{pastureId}/bovin/{bovinId}")
    public ResponseEntity<Void> updatePasture(@PathVariable Long pastureId, @PathVariable Long bovinId) {
        bovinService.updatePasture(pastureId, bovinId);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    public ResponseEntity<List<BovinSearchDTO>> findAllBovins() {
        return ResponseEntity.ok(bovinService.findAllBovins());
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<BovinDto> findBovinById(@PathVariable Long id) {
        return ResponseEntity.ok(bovinService.findBovinById(id));
    }
}
