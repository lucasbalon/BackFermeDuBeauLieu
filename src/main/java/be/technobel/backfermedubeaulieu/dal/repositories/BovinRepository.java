package be.technobel.backfermedubeaulieu.dal.repositories;

import be.technobel.backfermedubeaulieu.dal.models.Bull;
import be.technobel.backfermedubeaulieu.dal.models.Pasture;
import be.technobel.backfermedubeaulieu.dal.models.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BovinRepository<T extends Bull> extends JpaRepository<T, Long> {
    @Query("select t from Bull t where t.loopNumber like %:loopNumber% and t.status = 'ALIVE'")
    List<T> findAllByLoopNumber(String loopNumber);

    @Query("select t from Bull t where t.loopNumber = :loopNumber and t.status = 'ALIVE'")
    Optional<T> findByLoopNumber(String loopNumber);

    @Query("select t.loopNumber from Bull t where t.status = 'ALIVE'")
    String[] findAllBovinsLoopNumber();

    @Query("select t from Bull t where (t.father.id = :id or t.mother.id = :id) ")
    List<Bull> findChildren(Long id);

    @Modifying
    @Query("update Bull b set b.pasture.id = :pastureId where b.loopNumber = :bullLoopnumber")
    void updatePasture(Long pastureId, String bullLoopnumber);

    @Query("select t from Bull t where t.pasture.name = :name and t.status = 'ALIVE'")
    List<Bull> findAllBullsByPastureName(String name);

    @Query("select t from Bull t where t.pasture = :pasture and t.gender = true")
    Bull findFather(Pasture pasture);

    @Query("select t from Bull t where t.status = :status")
    List<Bull> findAllByStatus(Status status);


}