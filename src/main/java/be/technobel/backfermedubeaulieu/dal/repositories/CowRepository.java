package be.technobel.backfermedubeaulieu.dal.repositories;

import be.technobel.backfermedubeaulieu.dal.models.Bull;
import be.technobel.backfermedubeaulieu.dal.models.Cow;
import be.technobel.backfermedubeaulieu.dal.models.Pasture;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CowRepository extends BovinRepository<Cow> {
    @Query("select t.loopNumber from Bull t where t.gender = false ")
    String[] findAllBovinsLoopNumber();


    @Query("select t from Bull t where (t.gender = false) and ((t.pasture.id != :id) or (t.pasture.id is null))")
    List<Cow> findAvalaibleCowsByPasture(long id);
    @Query("select t from Bull t where (t.gender = false) and (t.pasture.id is null)")
    List<Cow> findAvalaibleCows();
    @Query("select t from Bull t where (t.gender = false) and t.pasture.id = :id")
    List<Cow> findCowInPasture(long id);
    @Modifying
    @Query("update Bull t set t.pasture = null where (t.gender = false) and t.loopNumber = :loopNumber")
    void removeFromPasture(String loopNumber);
}
