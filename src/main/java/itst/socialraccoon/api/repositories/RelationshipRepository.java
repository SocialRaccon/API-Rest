package itst.socialraccoon.api.repositories;

import itst.socialraccoon.api.models.RelationshipModel;
import itst.socialraccoon.api.models.compositekeys.RelationshipPK;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RelationshipRepository extends JpaRepository<RelationshipModel, RelationshipPK> {

    // Get followers of the user
    @Query(value = "SELECT * FROM relationship WHERE idFollower = :userId", nativeQuery = true)
    List<RelationshipModel> getFollowersByUserId(@Param("userId") Integer userId);

    //Get followers of the user paginated
    @Query(value = "SELECT * FROM relationship WHERE idFollower = :userId", nativeQuery = true)
    List<RelationshipModel> getFollowersByUserIdPaginated(@Param("userId") Integer userId, Pageable pageable);

    // Get following of the user
    @Query(value = "SELECT * FROM relationship WHERE idUser = :userId", nativeQuery = true) // Corrección aquí
    List<RelationshipModel> getFollowingByUserId(@Param("userId") Integer userId);

    //Get followers of the user paginated
    @Query(value = "SELECT * FROM relationship WHERE idUser = :userId", nativeQuery = true)
    List<RelationshipModel> getFollowingByUserIdPaginated(@Param("userId") Integer userId, Pageable pageable);
}