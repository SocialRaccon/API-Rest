package itst.social_raccoon_api.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import itst.social_raccoon_api.Models.ReactionModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReactionRepository extends JpaRepository<ReactionModel, Integer> {

    @Query(value = "SELECT * FROM reactions WHERE post_id = :post_id", nativeQuery = true)
    List<ReactionModel> getReactionsByPostId(@Param("post_id") int post_id);
}
