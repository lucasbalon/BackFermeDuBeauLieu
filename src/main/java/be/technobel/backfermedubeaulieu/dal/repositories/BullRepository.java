package be.technobel.backfermedubeaulieu.dal.repositories;

import be.technobel.backfermedubeaulieu.dal.models.Bull;
import org.springframework.data.jpa.repository.Query;

public interface BullRepository extends BovinRepository<Bull> {
    @Query("select t.loopNumber from Bull t where t.gender = true ")
    String[] findAllBovinsLoopNumber();

    @Query("select t.loopNumber from Bull t where t.gender = true and t.pasture.id = :id")
    String findBullByPastureId(long id);
}
