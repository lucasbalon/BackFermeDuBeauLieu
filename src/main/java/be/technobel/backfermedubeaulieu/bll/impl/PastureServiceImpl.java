package be.technobel.backfermedubeaulieu.bll.impl;

import be.technobel.backfermedubeaulieu.bll.services.PastureService;
import be.technobel.backfermedubeaulieu.dal.models.Pasture;
import be.technobel.backfermedubeaulieu.dal.repositories.PastureRepository;
import be.technobel.backfermedubeaulieu.pl.models.dtos.PastureDto;
import be.technobel.backfermedubeaulieu.pl.models.forms.PastureForm;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PastureServiceImpl implements PastureService {

    private final PastureRepository pastureRepository;

    public PastureServiceImpl(PastureRepository pastureRepository) {
        this.pastureRepository = pastureRepository;
    }
    @Override
    public List<PastureDto> getAllPastures() {
        return pastureRepository.findAll().stream().map(PastureDto::fromEntity).toList();
    }

    @Override
    public void savePasture(PastureForm pastureForm) {
        pastureRepository.save(Pasture.builder()
                .name(pastureForm.name())
                .size(pastureForm.size())
                .build());
    }

    @Override
    public Pasture findByName(String name) {
        return pastureRepository.findByName(name);
    }
}
