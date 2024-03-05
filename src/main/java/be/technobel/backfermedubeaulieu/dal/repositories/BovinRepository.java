package be.technobel.backfermedubeaulieu.dal.repositories;

import be.technobel.backfermedubeaulieu.dal.models.Bovin;
import be.technobel.backfermedubeaulieu.pl.models.dtos.searchBovin.BovinSearchDTO;
import be.technobel.backfermedubeaulieu.pl.models.forms.createBovin.BovinForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BovinRepository extends JpaRepository<Bovin, Long> {
    List<BovinSearchDTO> findBovinsByLoopNumber(int loopNumber);

}
