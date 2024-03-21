package be.technobel.backfermedubeaulieu.dal.repositories;

import be.technobel.backfermedubeaulieu.dal.models.Injection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InjectionRepository extends JpaRepository<Injection, Long> {
}
