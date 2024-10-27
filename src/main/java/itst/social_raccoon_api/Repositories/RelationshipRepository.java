package itst.social_raccoon_api.Repositories;

import itst.social_raccoon_api.Models.CompositeKeys.RelationshipPK;
import itst.social_raccoon_api.Models.RelationshipModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RelationshipRepository extends JpaRepository<RelationshipModel, RelationshipPK> {

    // Get followers of the user
    @Query(value = "SELECT * FROM relationship WHERE idFollower = :userId", nativeQuery = true)
    List<RelationshipModel> getFollowersByUserId(@Param("userId") Integer userId);

    // Get following of the user
    @Query(value = "SELECT * FROM relationship WHERE idUser = :userId", nativeQuery = true) // Corrección aquí
    List<RelationshipModel> getFollowingByUserId(@Param("userId") Integer userId);
}