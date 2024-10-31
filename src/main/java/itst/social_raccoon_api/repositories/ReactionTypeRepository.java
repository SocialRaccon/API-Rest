package itst.social_raccoon_api.repositories;

import itst.social_raccoon_api.models.ReactionTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReactionTypeRepository extends JpaRepository<ReactionTypeModel, Integer> {
    @Query(value = "SELECT * FROM reaction_type WHERE reactionType = :name", nativeQuery = true)
    ReactionTypeModel getByName(@Param("name") String name);
}
