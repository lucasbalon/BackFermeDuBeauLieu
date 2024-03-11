package be.technobel.backfermedubeaulieu.dal.repositories;

import be.technobel.backfermedubeaulieu.dal.models.Bull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BovinRepository<T extends Bull> extends JpaRepository<T, Long> {
    @Query("select t from Bull t where t.loopNumber like %:loopNumber%")
    List<T> findAllByLoopNumber(String loopNumber);

    @Query("select t from Bull t where t.loopNumber = :loopNumber")
    Optional<T> findByLoopNumber(String loopNumber);


}
