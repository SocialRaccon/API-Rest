package itst.socialraccoon.api.repositories;

import itst.socialraccoon.api.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {

    UserModel findByAuthentication_Email(String email);

}
