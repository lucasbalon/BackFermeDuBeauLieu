package be.technobel.backfermedubeaulieu.dal.repositories;

import be.technobel.backfermedubeaulieu.dal.models.Pasture;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PastureRepository extends JpaRepository<Pasture, Long> {
    Pasture findByName(String name);
}
