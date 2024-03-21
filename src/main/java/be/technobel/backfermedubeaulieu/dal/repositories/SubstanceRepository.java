package be.technobel.backfermedubeaulieu.dal.repositories;

import be.technobel.backfermedubeaulieu.dal.models.Substance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubstanceRepository extends JpaRepository<Substance, Long> {
    Optional<Substance> findByName(String name);
}
