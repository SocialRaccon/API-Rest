package itst.social_raccoon_api.Repository;

import itst.social_raccoon_api.Models.ProfileModel;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<ProfileModel, Long> {
    
}
