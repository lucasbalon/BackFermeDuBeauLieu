package be.technobel.backfermedubeaulieu.pl.controllers;

import be.technobel.backfermedubeaulieu.bll.services.ScanService;
import be.technobel.backfermedubeaulieu.pl.models.forms.ScanForm;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/scan")
public class ScanController {
    private final ScanService scanService;

    public ScanController(ScanService scanService) {
        this.scanService = scanService;
    }

    @PostMapping()
    public void createScan(@RequestBody ScanForm scanForm) {
        scanService.create(scanForm);
    }
}
