package be.technobel.backfermedubeaulieu.dal.repositories;

import be.technobel.backfermedubeaulieu.dal.models.Bull;
import be.technobel.backfermedubeaulieu.dal.models.Pasture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PastureRepository extends JpaRepository<Pasture, Long> {
    Pasture findByName(String name);
}
