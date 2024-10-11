package itst.social_raccoon_api.Repository;

import itst.social_raccoon_api.Models.AuthenticationModel;
import org.springframework.data.repository.CrudRepository;

public interface AuthenticationRepository extends CrudRepository<AuthenticationModel, Long> {
    
}
