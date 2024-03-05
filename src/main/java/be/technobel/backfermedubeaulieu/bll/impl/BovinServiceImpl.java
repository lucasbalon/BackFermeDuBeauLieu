package be.technobel.backfermedubeaulieu.bll.impl;

import be.technobel.backfermedubeaulieu.bll.services.BovinService;
import be.technobel.backfermedubeaulieu.dal.repositories.BovinRepository;
import be.technobel.backfermedubeaulieu.pl.models.dtos.searchBovin.BovinSearchDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BovinServiceImpl implements BovinService {
    private final BovinRepository bovinRepository;

    public BovinServiceImpl(BovinRepository bovinRepository) {
        this.bovinRepository = bovinRepository;
    }

    @Override
    public List<BovinSearchDTO> findBovinByLoopNumber(int loopNumber) {
        return bovinRepository.findBovinByLoopNumber(loopNumber);
    }
}
