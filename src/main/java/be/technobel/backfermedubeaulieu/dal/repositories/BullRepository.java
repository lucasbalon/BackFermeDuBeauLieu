package be.technobel.backfermedubeaulieu.dal.repositories;

import be.technobel.backfermedubeaulieu.dal.models.Bull;
import be.technobel.backfermedubeaulieu.pl.models.dtos.searchBovin.BovinSearchDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BullRepository extends BovinRepository<Bull> {
}
