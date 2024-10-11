package itst.social_raccoon_api.Repository;

import itst.social_raccoon_api.Models.UserModel;
import java.util.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<UserModel, Long> {
    
    @Query(value = "SELECT * FROM user WHERE name = :nombre", nativeQuery = true )
    List<UserModel> getUserByName(@Param("nombre")String name);
}
