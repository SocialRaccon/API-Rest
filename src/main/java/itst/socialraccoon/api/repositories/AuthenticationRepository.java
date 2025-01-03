package itst.socialraccoon.api.repositories;

import itst.socialraccoon.api.models.AuthenticationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AuthenticationRepository extends JpaRepository<AuthenticationModel, Integer> {
    @Query( value = "SELECT * FROM authentication a WHERE a.email = :email", nativeQuery = true)
    AuthenticationModel findByEmail(String email);
}
