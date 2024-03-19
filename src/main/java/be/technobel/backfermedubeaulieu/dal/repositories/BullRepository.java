package be.technobel.backfermedubeaulieu.dal.repositories;

import be.technobel.backfermedubeaulieu.dal.models.Bull;
import be.technobel.backfermedubeaulieu.dal.models.Pasture;
import be.technobel.backfermedubeaulieu.pl.models.dtos.BovinShortDTO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BullRepository extends BovinRepository<Bull> {
    @Query("select t.loopNumber from Bull t where t.gender = true ")
    String[] findAllBovinsLoopNumber();

    @Query("select t.loopNumber from Bull t where (t.gender = true) and t.pasture.id = :id")
    String findBullLoopNumberByPastureId(long id);

    @Modifying
    @Query("update Bull t set t.pasture = null where (t.gender = true) and t.pasture.id = :id")
    void deleteAllByPasture(long id);


    @Query("select t from Bull t where (t.gender = true) and t.pasture.id = :id")
    Optional<Bull> findBullByPastureId(long id);

    @Query("select t from Bull t where (t.gender = true) and (t.pasture.id is null)")
    List<Bull> findAvailableBull();
}
