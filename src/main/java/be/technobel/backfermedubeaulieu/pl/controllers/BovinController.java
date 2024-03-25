package be.technobel.backfermedubeaulieu.pl.controllers;

import be.technobel.backfermedubeaulieu.bll.services.BovinService;
import be.technobel.backfermedubeaulieu.pl.models.dtos.BovinDto;
import be.technobel.backfermedubeaulieu.pl.models.dtos.BovinShortDTO;
import be.technobel.backfermedubeaulieu.pl.models.dtos.PastureFullDTO;
import be.technobel.backfermedubeaulieu.pl.models.dtos.searchBovin.BovinSearchDTO;
import be.technobel.backfermedubeaulieu.pl.models.forms.createBovin.BovinForm;
import be.technobel.backfermedubeaulieu.pl.models.forms.createBovin.ShortBovinForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
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
        return ResponseEntity.ok(bovinService.findBullLoopnumberByPastureId(id));
    }
    @GetMapping
    public ResponseEntity<List<BovinSearchDTO>> findAllBovins() {
        return ResponseEntity.ok(bovinService.findAllBovins());
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<BovinDto> findBovinById(@PathVariable Long id) {
        return ResponseEntity.ok(bovinService.findBovinById(id));
    }
    @GetMapping("/bull/available")
    public ResponseEntity<List<BovinShortDTO>> findAvailableBull() {
        return ResponseEntity.ok(bovinService.findAvailableBull());
    }


    //PASTURE



    @GetMapping("/pasture/{id}")
    public ResponseEntity<PastureFullDTO> findPasture(@PathVariable long id) {
        return ResponseEntity.ok(bovinService.findPasture(id));
    }
    @PatchMapping("/bull/{bullLoopnumber}/pasture")
    public ResponseEntity<Void> updatePastureBull(@RequestBody Long pastureId, @PathVariable String bullLoopnumber) {
        bovinService.updatePastureBull(pastureId, bullLoopnumber);
        return ResponseEntity.ok().build();
    }
    @PatchMapping("/cow/{cowLoopnumber}/pasture")
    public ResponseEntity<Void> updatePasture(@RequestBody Long pastureId, @PathVariable String cowLoopnumber) {
        bovinService.updatePasture(pastureId, cowLoopnumber);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/cow/{loopNumber}")
    public ResponseEntity<Void> removeCowFromPasture(@PathVariable String loopNumber) {
        bovinService.removeCowFromPasture(loopNumber);
        return ResponseEntity.ok().build();
    }


}
