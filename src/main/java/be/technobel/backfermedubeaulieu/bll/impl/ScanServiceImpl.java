package be.technobel.backfermedubeaulieu.bll.impl;

import be.technobel.backfermedubeaulieu.bll.services.ScanService;
import be.technobel.backfermedubeaulieu.dal.models.Scan;
import be.technobel.backfermedubeaulieu.dal.repositories.CowRepository;
import be.technobel.backfermedubeaulieu.dal.repositories.ScanRepository;
import be.technobel.backfermedubeaulieu.pl.models.forms.ScanForm;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ScanServiceImpl implements ScanService {
    private final ScanRepository scanRepository;
    private final CowRepository cowRepository;

    public ScanServiceImpl(ScanRepository scanRepository, CowRepository cowRepository) {
        this.scanRepository = scanRepository;
        this.cowRepository = cowRepository;
    }


    @Override
    public void create(ScanForm scanForm) {
        scanRepository.save(
                new Scan(
                        scanForm.scan_date(),
                        scanForm.result(),
                        cowRepository.findByLoopNumber(scanForm.loopNumber()).orElseThrow(() -> new EntityNotFoundException("Vache introuvable !"))
                )
        );
    }
}
