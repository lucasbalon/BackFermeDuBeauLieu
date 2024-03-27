package be.technobel.backfermedubeaulieu.dal.repositories;

import be.technobel.backfermedubeaulieu.dal.models.Bull;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BullRepository extends BovinRepository<Bull> {
    @Query("select t.loopNumber from Bull t where t.gender = true  and t.status = 'ALIVE'")
    String[] findAllBovinsLoopNumber();

    @Query("select t.loopNumber from Bull t where (t.gender = true) and t.pasture.id = :id")
    String findBullLoopNumberByPastureId(long id);

    @Modifying
    @Query("update Bull t set t.pasture = null where (t.gender = true) and t.pasture.id = :id")
    void deleteAllByPasture(long id);


    @Query("select t from Bull t where (t.gender = true) and t.pasture.id = :id and t.status = 'ALIVE'")
    Optional<Bull> findBullByPastureId(long id);

    @Query("select t from Bull t where (t.gender = true) and (t.pasture.id is null) and t.status = 'ALIVE'")
    List<Bull> findAvailableBull();
}
