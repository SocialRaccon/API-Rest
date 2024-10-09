package itst.social_raccoon_api.Repositories;

import itst.social_raccoon_api.Models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
}
