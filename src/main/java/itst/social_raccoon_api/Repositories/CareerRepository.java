package itst.social_raccoon_api.Repositories;

import itst.social_raccoon_api.Models.CareerModel;
import itst.social_raccoon_api.Models.UserModel;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CareerRepository extends JpaRepository<CareerModel, Integer> {

    //Get all usersModels by career id
    @Query(value = "SELECT u FROM UserModel u WHERE u.career.id = :careerId")
    List<UserModel> getUsersByCareerId(@Param("careerId") Integer careerId);
}
