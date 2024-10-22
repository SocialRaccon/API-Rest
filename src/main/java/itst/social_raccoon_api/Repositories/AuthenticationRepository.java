package itst.social_raccoon_api.Repositories;

import itst.social_raccoon_api.Models.AuthenticationModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationRepository extends JpaRepository<AuthenticationModel, Integer> {
    
}
