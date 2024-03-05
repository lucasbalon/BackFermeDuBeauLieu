package be.technobel.backfermedubeaulieu.dal.repositories;

import be.technobel.backfermedubeaulieu.dal.models.Bovin;
import be.technobel.backfermedubeaulieu.pl.models.dtos.searchBovin.BovinSearchDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BovinRepository extends JpaRepository<Bovin, Long> {
    public List<BovinSearchDTO> findBovinByLoopNumber(int loopNumber);
}
