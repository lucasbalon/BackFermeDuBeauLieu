package be.technobel.backfermedubeaulieu.dal.repositories;

import be.technobel.backfermedubeaulieu.dal.models.Cow;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CowRepository extends BovinRepository<Cow> {
    @Query("select t.loopNumber from Bull t where t.gender = false  and t.status = 'ALIVE'")
    String[] findAllBovinsLoopNumber();


    @Query("select t from Bull t where (t.gender = false) and (t.pasture.id is null) and t.status = 'ALIVE'")
    List<Cow> findAvalaibleCowsByPasture(long id);

    @Query("select t from Bull t where (t.gender = false) and (t.pasture.id is null)")
    List<Cow> findAvalaibleCows();

    @Query("select t from Bull t where (t.gender = false) and t.pasture.id = :id and t.status = 'ALIVE'")
    List<Cow> findCowInPasture(long id);

    @Modifying
    @Query("update Bull t set t.pasture = null where (t.gender = false) and t.loopNumber = :loopNumber")
    void removeFromPasture(String loopNumber);
}
