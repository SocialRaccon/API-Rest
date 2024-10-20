package itst.social_raccoon_api.Repositories;

import itst.social_raccoon_api.Models.CompositeKeys.FollowerPK;
import itst.social_raccoon_api.Models.FollowerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FollowerRepository extends JpaRepository<FollowerModel, FollowerPK> {

    // Get followers of the user
    @Query(value = "SELECT * FROM follower WHERE idUser = :userId", nativeQuery = true)
    List<FollowerModel> getFollowersByUserId(@Param("userId") Integer userId);

    // Get users followed by the user
    @Query(value = "SELECT * FROM follower WHERE idFollower = :followerId", nativeQuery = true)
    List<FollowerModel> getFollowersByFollowerId(@Param("followerId") Integer followerId);
}
