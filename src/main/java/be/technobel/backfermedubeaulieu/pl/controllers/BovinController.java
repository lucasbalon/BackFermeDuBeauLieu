package be.technobel.backfermedubeaulieu.pl.controllers;

import be.technobel.backfermedubeaulieu.bll.services.BovinService;
import be.technobel.backfermedubeaulieu.dal.models.enums.Status;
import be.technobel.backfermedubeaulieu.pl.models.dtos.BovinDto;
import be.technobel.backfermedubeaulieu.pl.models.dtos.BovinShortDTO;
import be.technobel.backfermedubeaulieu.pl.models.dtos.PastureFullDTO;
import be.technobel.backfermedubeaulieu.pl.models.dtos.searchBovin.BovinSearchDTO;
import be.technobel.backfermedubeaulieu.pl.models.forms.createBovin.BovinForm;
import be.technobel.backfermedubeaulieu.pl.models.forms.createBovin.ShortBovinForm;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bovin")
public class BovinController {
    private final BovinService bovinService;

    public BovinController(BovinService bovinService) {
        this.bovinService = bovinService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{loopNumber}")
    public ResponseEntity<List<BovinSearchDTO>> findBovinsByLoopNumber(@PathVariable String loopNumber) {
        return ResponseEntity.ok(bovinService.findBovinsByLoopNumber(loopNumber));
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<Void> createBovin(@Valid @RequestBody BovinForm bovinForm) {
        bovinService.createBovin(bovinForm);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/short")
    public ResponseEntity<Void> shortCreateBovin(@Valid @RequestBody ShortBovinForm shortBovinForm) {
        bovinService.shortCreateBovin(shortBovinForm);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/loopNumbers")
    public ResponseEntity<String[]> findAllBovinsLoopNumber() {
        return ResponseEntity.ok(bovinService.findAllBovinsLoopNumber());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/loopNumbers/cow")
    public ResponseEntity<String[]> findAllCowsLoopNumber() {
        return ResponseEntity.ok(bovinService.findAllCowLoopNumber());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/loopNumbers/bull")
    public ResponseEntity<String[]> findAllBullsLoopNumber() {
        return ResponseEntity.ok(bovinService.findAllBullLoopNumber());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/bull/pasture/{id}")
    public ResponseEntity<String> findAllBullsByPastureName(@PathVariable long id) {
        return ResponseEntity.ok(bovinService.findBullLoopnumberByPastureId(id));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<BovinSearchDTO>> findAllBovins() {
        return ResponseEntity.ok(bovinService.findAllBovins());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/id/{id}")
    public ResponseEntity<BovinDto> findBovinById(@PathVariable Long id) {
        return ResponseEntity.ok(bovinService.findBovinById(id));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/bull/available")
    public ResponseEntity<List<BovinShortDTO>> findAvailableBull() {
        return ResponseEntity.ok(bovinService.findAvailableBull());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<BovinSearchDTO>> findAllBullsByStatus(@PathVariable Status status) {
        return ResponseEntity.ok(bovinService.findAllByStatus(status).stream().map(BovinSearchDTO::fromEntity).toList());
    }


    //PASTURE


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/pasture/{id}")
    public ResponseEntity<PastureFullDTO> findPasture(@PathVariable long id) {
        return ResponseEntity.ok(bovinService.findPasture(id));
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/bull/{bullLoopnumber}/pasture")
    public ResponseEntity<Void> updatePastureBull(@RequestBody Long pastureId, @PathVariable String bullLoopnumber) {
        bovinService.updatePastureBull(pastureId, bullLoopnumber);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/cow/{cowLoopnumber}/pasture")
    public ResponseEntity<Void> updatePasture(@RequestBody Long pastureId, @PathVariable String cowLoopnumber) {
        bovinService.updatePasture(pastureId, cowLoopnumber);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/cow/{loopNumber}")
    public ResponseEntity<Void> removeCowFromPasture(@PathVariable String loopNumber) {
        bovinService.removeCowFromPasture(loopNumber);
        return ResponseEntity.ok().build();
    }


}
