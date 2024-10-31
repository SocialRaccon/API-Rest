package itst.social_raccoon_api.repositories;

import itst.social_raccoon_api.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
}
