package be.technobel.backfermedubeaulieu.dal.repositories;

import be.technobel.backfermedubeaulieu.dal.models.Cow;
import org.springframework.data.jpa.repository.Query;

public interface CowRepository extends BovinRepository<Cow> {
    @Query("select t.loopNumber from Bull t where t.gender = false ")
    String[] findAllBovinsLoopNumber();
}
