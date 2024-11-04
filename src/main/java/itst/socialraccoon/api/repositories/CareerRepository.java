package itst.socialraccoon.api.repositories;

import itst.socialraccoon.api.models.CareerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CareerRepository extends JpaRepository<CareerModel, Integer> {

    //career name
    @Query(value = "SELECT * FROM career WHERE acronym = :acronym", nativeQuery = true)
    CareerModel getByAcronym(@Param("acronym") String acronym);
}
