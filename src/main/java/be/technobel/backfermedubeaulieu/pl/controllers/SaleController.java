package be.technobel.backfermedubeaulieu.pl.controllers;

import be.technobel.backfermedubeaulieu.bll.services.SaleService;
import be.technobel.backfermedubeaulieu.pl.models.forms.SaleForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sale")
public class SaleController {
    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }
    @PostMapping
    public ResponseEntity<Void> createSale(@RequestBody SaleForm saleForm) {
        saleService.createSale(saleForm);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
