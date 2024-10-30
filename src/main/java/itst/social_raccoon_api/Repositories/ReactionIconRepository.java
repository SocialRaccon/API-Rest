package itst.social_raccoon_api.Repositories;

import itst.social_raccoon_api.Models.ReactionIconModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface ReactionIconRepository extends JpaRepository<ReactionIconModel, Integer> {
    @Query(value = "SELECT * FROM reaction_icon WHERE idReactionType = :reactionTypeId", nativeQuery = true)
    List<ReactionIconModel> getReactionIconByReactionTypeId(@Param("reactionTypeId") Integer reactionTypeId);

    @Query(value = "SELECT * FROM reaction_icon WHERE idReactionType = :reactionTypeId \n-- #pageable\n", nativeQuery = true)
    List<ReactionIconModel> getReactionIconByReactionTypeId(@Param("reactionTypeId") Integer reactionTypeId, Pageable pageable);

    @Query(value = "SELECT * FROM reaction_icon WHERE idReactionType = :reactionTypeId AND idReactionIcon = :reactionIconId", nativeQuery = true)
    ReactionIconModel getReactionIcon(@Param("reactionTypeId") Integer reactionTypeId, @Param("reactionIconId") Integer reactionIconId);

    @Query(value = "DELETE FROM reaction_icon WHERE idReactionType = :reactionTypeId AND idReactionIcon = :reactionIconId", nativeQuery = true)
    void deleteReactionIcon(@Param("reactionTypeId") Integer reactionTypeId, @Param("reactionIconId") Integer reactionIconId);
}

