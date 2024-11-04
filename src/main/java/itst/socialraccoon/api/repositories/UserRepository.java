package itst.socialraccoon.api.repositories;

import itst.socialraccoon.api.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
}
