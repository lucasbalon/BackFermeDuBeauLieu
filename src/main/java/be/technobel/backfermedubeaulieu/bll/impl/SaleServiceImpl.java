package be.technobel.backfermedubeaulieu.bll.impl;

import be.technobel.backfermedubeaulieu.bll.services.BovinService;
import be.technobel.backfermedubeaulieu.bll.services.SaleService;
import be.technobel.backfermedubeaulieu.dal.models.Bull;
import be.technobel.backfermedubeaulieu.dal.models.Sale;
import be.technobel.backfermedubeaulieu.dal.models.enums.Status;
import be.technobel.backfermedubeaulieu.dal.repositories.BullRepository;
import be.technobel.backfermedubeaulieu.dal.repositories.SaleRepository;
import be.technobel.backfermedubeaulieu.pl.config.exceptions.AlreadyDeadException;
import be.technobel.backfermedubeaulieu.pl.models.forms.SaleForm;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final BovinService bovinService;

    public SaleServiceImpl(SaleRepository saleRepository, BovinService bovinService) {
        this.saleRepository = saleRepository;
        this.bovinService = bovinService;
    }

    @Override
    public void createSale(SaleForm saleForm) {
        Bull bull = bovinService.findByLoopNumber(saleForm.bovinLoopNumber());
        if (bull.getStatus() == Status.SOLD || bull.getStatus() == Status.DEAD) {
            throw new AlreadyDeadException("Bovin déjà mort");
        }
        Sale sale = new Sale(
                saleForm.saleDate(),
                saleForm.amount(),
                saleForm.carrierNumber(),
                saleForm.customerNumber(),
                bull
        );
        saleRepository.save(sale);
        bovinService.setStatus(Status.SOLD, saleForm.bovinLoopNumber());
    }
}
