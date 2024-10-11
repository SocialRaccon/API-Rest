package itst.social_raccoon_api.Repository;

import itst.social_raccoon_api.Models.CareerModel;
import org.springframework.data.repository.CrudRepository;

public interface CareerRepository extends CrudRepository<CareerModel, Long> {
    
}
