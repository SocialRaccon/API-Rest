package itst.social.raccoon.api.repositories;

import itst.social.raccoon.api.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
}
